package db

import (
	"bytes"
	"encoding/hex"
	"errors"
	"fmt"
	"starcoin-ns-demo/tools"

	"github.com/celestiaorg/smt"
	"github.com/go-sql-driver/mysql"
	gormmysql "gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"
)

const (
	KEY_STARCOIN_HEIGHT string = "STARCOIN_HEIGHT"
)

type MySqlDB struct {
	//rwlock   *sync.RWMutex
	db *gorm.DB
}

func NewMySqlDB(dsn string) (*MySqlDB, error) {
	db, err := gorm.Open(gormmysql.Open(dsn), &gorm.Config{
		NamingStrategy: schema.NamingStrategy{
			SingularTable: true,
		},
	})
	if err != nil {
		return nil, err
	}
	// Migrate the schema
	//db.AutoMigrate(&ChainHeight{})
	db.Set("gorm:table_options", "CHARSET=latin1").AutoMigrate(
		&ChainHeight{},
		&DomainNameSmtNode{},
		&DomainNameSmtValue{},
		&DomainNameEvent{},
		&DomainNameEventSequence{},
		&DomainNameState{},
		&DomainNameStateHead{},
	)

	w := new(MySqlDB)
	w.db = db
	return w, nil
}

func (w *MySqlDB) GetDomainNameSmtValue(path string, valueHash string) (*DomainNameSmtValue, error) {
	v := new(DomainNameSmtValue)
	if err := w.db.Where(&DomainNameSmtValue{
		Path:      path,
		ValueHash: valueHash,
	}).First(v).Error; err != nil {
		//if !errors.Is(err, gorm.ErrRecordNotFound) {
		return nil, err
		// } else {
		// 	//fmt.Println("errors.Is(err, gorm.ErrRecordNotFound)")
		// 	return nil, nil
		// }
	}
	return v, nil
}

func (w *MySqlDB) CreateDomainNameEvent(e *DomainNameEvent) error {
	err := w.db.Create(e).Error
	var mysqlErr *mysql.MySQLError
	if errors.As(err, &mysqlErr) && mysqlErr.Number == 1062 { // if it is Duplicate-entry DB error
		// can only re-create last DomainNameEvent
		existedE, err := w.GetDomainNameEventByBlockHashAndEventKey(e.BlockHash, e.EventKey)
		if err != nil {
			return err
		}
		if existedE != nil {
			// ignore err?
			return nil
			// maxId, err := w.GetDomainNameEventMaxId()
			// if err != nil {
			// 	return err
			// }
			// if existedE.Id != maxId {
			// 	return fmt.Errorf(
			// 		"re-create DomainNameEvent(BlockHash: %s, EventKey: %s, Id: %d) which is not last event, max event ID: %d",
			// 		existedE.BlockHash, existedE.EventKey, existedE.Id, maxId) // think about removing this event then go on
			// }
		}
		// ignore error?
		return nil
	}
	return err
}

// Get DomainNameEvent by block hash and event key(DomainNameEvent's composite domain key).
func (w *MySqlDB) GetDomainNameEventByBlockHashAndEventKey(blockHash string, eventKey string) (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Where("block_hash = ? and event_key = ?", blockHash, eventKey).Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}
func (w *MySqlDB) GetDomainNameEvent(id uint64) (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Where("id = ?", id).Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetDomainNameEventMaxId() (uint64, error) {
	var list []DomainNameEvent
	if err := w.db.Select("id").Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return 0, err
	}
	if len(list) == 0 {
		return 0, nil
	}
	first := list[0]
	return first.Id, nil
}

func (w *MySqlDB) GetFirstDomainNameEvent() (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Order("id").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetLastDomainNameEvent() (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetPreviousDomainNameEvent(e *DomainNameEvent) (*DomainNameEvent, error) {
	if e.PreviousSmtRoot == "" {
		return nil, nil
	}
	var list []DomainNameEvent
	if err := w.db.Where("id < ? and updated_smt_root = ?", e.Id, e.PreviousSmtRoot).Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetLastDomainNameEventByIdLessThan(id uint64) (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Where("id < ?", id).Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetLastDomainNameEventByPreviousSmtRoot(r string) (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Where("previous_smt_root = ?", r).Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetLastDomainNameEventByBlockNumberLessThan(blockNumber uint64) (*DomainNameEvent, error) {
	var list []DomainNameEvent
	if err := w.db.Where("block_number < ?", blockNumber).Order("id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) GetDomainNameEventSequence(seqId string) (*DomainNameEventSequence, error) {
	es := new(DomainNameEventSequence)
	if err := w.db.Where(&DomainNameEventSequence{
		SequenceId: seqId,
	}).First(es).Error; err != nil {
		return nil, err
	}
	return es, nil
}

func (w *MySqlDB) GetDomainNameEventSequenceAllElementIds(es *DomainNameEventSequence) ([]uint64, error) {
	idSlices := make([][]uint64, 0)
	currentES := es
	for {
		var err error
		idSlice, err := DecodeEventIds(currentES.ElementIds)
		if err != nil {
			return nil, err
		}
		idSlices = append(idSlices, idSlice)
		if currentES.PreviousSequenceId == "" {
			break
		}
		currentES, err = w.GetDomainNameEventSequence(currentES.PreviousSequenceId)
		if err != nil {
			return nil, err
		}
		if currentES == nil {
			break
		}
	}
	idSlices = reverseUint64Slices(idSlices)
	ids := make([]uint64, 0)
	for i, v := range idSlices {
		if len(v) == 0 {
			return nil, fmt.Errorf("empty slice, index: %d", i)
		}
		if len(ids) > 0 && ids[len(ids)-1] == v[0] {
			ids = append(ids, v[1:]...)
		} else {
			if len(ids) > 0 && ids[len(ids)-1] == v[0] {
				return nil, fmt.Errorf("cannot concatenate slice(...%d) and slice(%d...)", ids[len(ids)-1], v[0])
			}
			ids = append(ids, v...)
		}
	}
	return ids, nil
}

func (w *MySqlDB) GetLastDomainNameEventSequenceByLastEventIdLessThanOrEquals(eventId uint64) (*DomainNameEventSequence, error) {
	var list []DomainNameEventSequence
	if err := w.db.Where("last_event_id <= ?", eventId).Order("last_event_id desc").Limit(1).Find(&list).Error; err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func (w *MySqlDB) CreateDomainNameEventSequence(es *DomainNameEventSequence) error {
	err := w.db.Create(es).Error
	var mysqlErr *mysql.MySQLError
	if errors.As(err, &mysqlErr) && mysqlErr.Number == 1062 { // if it is Duplicate-entry DB error
		// oldData, err := m.Get(key)
		// if err != nil {
		// 	return err
		// }
		// if bytes.Equal(value, oldData) { // if it is really duplicate entry
		// 	return nil
		// } else {
		// 	return fmt.Errorf("reset value is not allowed, key: %s, value: %s, old value: %s", h, d, hex.EncodeToString(oldData))
		// }
		return nil
	}
	return err
}

func (w *MySqlDB) UpdateDomainNameStateByEvent(e *DomainNameEvent) error {
	headId := getDomainNameStateHeadIdByEvent(e)
	err := w.db.Transaction(func(tx *gorm.DB) error {
		s, err := getDomainNameState(tx, e.DomainNameIdTopLevelDomain, e.DomainNameIdSecondLevelDomain)
		if err != nil {
			return err
		}
		if s == nil {
			updatedStateOwner, err := e.GetUpdatedStateOwner()
			if err != nil {
				return err
			}
			s = NewDomainNameState(e.GetDomainNameId(), e.UpdatedStateExpirationDate, updatedStateOwner)
			s.CreatedAtBlockNumber = e.BlockNumber
			s.UpdatedAtBlockNumber = e.BlockNumber
			if err = tx.Create(s).Error; err != nil {
				return err
			}
		} else {
			s.ExpirationDate = e.UpdatedStateExpirationDate
			updatedStateOwner, err := e.GetUpdatedStateOwner()
			if err != nil {
				return err
			}
			s.SetOwner(updatedStateOwner)
			s.UpdatedAtBlockNumber = e.BlockNumber
			if err = tx.Save(s).Error; err != nil {
				return err
			}
		}

		h, err := getDomainNameStateHead(tx, headId)
		if err != nil {
			return err
		}
		if h == nil {
			h := NewDomainNameStateHead(headId, e.BlockHash, e.EventKey, e.UpdatedSmtRoot, DOMAIN_NAME_STATE_DEFAULT_TABLE_NAME)
			if err = tx.Create(h).Error; err != nil {
				return err
			}
		} else {
			h.BlockHash = e.BlockHash
			h.EventKey = e.EventKey
			h.SmtRoot = e.UpdatedSmtRoot
			dbUpdated := tx.Model(&DomainNameStateHead{}).Where(
				"head_id = ? and smt_root = ?", headId, e.PreviousSmtRoot,
			).Updates(h)
			if dbUpdated.Error != nil {
				return err
			}
			rowsAffected := dbUpdated.RowsAffected
			if rowsAffected == 0 {
				return fmt.Errorf("optimistic lock error. headId: %s, smtRoot: %s", headId, e.PreviousSmtRoot)
			}
		}
		return nil
	})
	return err
}

func (w *MySqlDB) GetDefaultDomainNameStateHead() (*DomainNameStateHead, error) {
	return getDomainNameStateHead(w.db, DOMAIN_NAME_STATE_HEAD_ID_DEFAULT)
}

func getDomainNameStateHeadIdByEvent(e *DomainNameEvent) string {
	headId := DOMAIN_NAME_STATE_HEAD_ID_DEFAULT //todo
	return headId
}

func getDomainNameState(database *gorm.DB, tld string, sld string) (*DomainNameState, error) {
	var list []DomainNameState
	err := database.Where(&DomainNameState{
		DomainNameIdTopLevelDomain:    tld,
		DomainNameIdSecondLevelDomain: sld,
	}).Limit(1).Find(&list).Error
	if err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func getDomainNameStateHead(database *gorm.DB, headId string) (*DomainNameStateHead, error) {
	var list []DomainNameStateHead
	err := database.Where(&DomainNameStateHead{
		HeadId: headId,
	}).Limit(1).Find(&list).Error
	if err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

// //////////////// Map Store interface ////////////////////
//Get(key []byte) ([]byte, error)     // Get gets the value for a key.
//Set(key []byte, value []byte) error // Set updates the value for a key.
//Delete(key []byte) error            // Delete deletes a key.

type DomainNameSmtNodeMapStore struct {
	db *MySqlDB
}

func (db *MySqlDB) NewDomainNameSmtNodeMapStore() (smt.MapStore, error) {
	return &DomainNameSmtNodeMapStore{
		db: db,
	}, nil
}

// The 'Get' gets the value for a key.
func (m *DomainNameSmtNodeMapStore) Get(key []byte) ([]byte, error) {
	h := hex.EncodeToString(key)
	n := &DomainNameSmtNode{}
	if err := m.db.db.Where(&DomainNameSmtNode{
		Hash: h,
	}).First(n).Error; err != nil {
		if !errors.Is(err, gorm.ErrRecordNotFound) {
			return nil, err
		} else {
			//fmt.Println("errors.Is(err, gorm.ErrRecordNotFound)")
			return nil, &smt.InvalidKeyError{Key: key}
		}
	}
	d, err := hex.DecodeString(n.Data)
	if err != nil {
		return nil, err
	}
	//fmtPrintlnNodeData(d)
	return d, nil
}

// The 'Set' function updates the `value` of the `key``.
func (m *DomainNameSmtNodeMapStore) Set(key []byte, value []byte) error {
	h := hex.EncodeToString(key)
	d := hex.EncodeToString(value)
	n := &DomainNameSmtNode{
		Hash: h,
		Data: d,
	}
	err := m.db.db.Create(n).Error
	var mysqlErr *mysql.MySQLError
	if errors.As(err, &mysqlErr) && mysqlErr.Number == 1062 { // if it is Duplicate-entry DB error
		oldData, err := m.Get(key)
		if err != nil {
			return err
		}
		if bytes.Equal(value, oldData) { // if it is really duplicate entry
			return nil
		} else {
			return fmt.Errorf("reset value is not allowed, key: %s, value: %s, old value: %s", h, d, hex.EncodeToString(oldData))
		}
	}
	return err
}

// The 'Delete' function deletes a key.
func (m *DomainNameSmtNodeMapStore) Delete(key []byte) error {
	// do nothing
	return nil
}

type DomainNameSmtValueMapStore struct {
	db *MySqlDB
}

func (db *MySqlDB) NewDomainNameSmtValueMapStore() smt.SmtValueStore {
	return &DomainNameSmtValueMapStore{
		db: db,
	}
}

func (m *DomainNameSmtValueMapStore) Immutable() bool {
	return true
}

func (m *DomainNameSmtValueMapStore) Get(key []byte) ([]byte, error) {
	return nil, fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Get")
}

func (m *DomainNameSmtValueMapStore) Set(key []byte, value []byte) error {
	return fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Set")
}

// Delete deletes a key.
func (m *DomainNameSmtValueMapStore) Delete(key []byte) error {
	// path := hex.EncodeToString(key)
	// _ = path
	return fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Delete")
}

// Get gets the value for a key(path).
func (m *DomainNameSmtValueMapStore) GetForValueHash(key []byte, valueHash []byte) ([]byte, error) {
	path := hex.EncodeToString(key)
	h := hex.EncodeToString(valueHash)
	domainNameSmtValue, err := m.db.GetDomainNameSmtValue(path, h)
	if err != nil {
		return nil, err
	}
	return hex.DecodeString(domainNameSmtValue.Value)
}

// Set updates the value for a key(path).
func (m *DomainNameSmtValueMapStore) SetForValueHash(key []byte, valueHash []byte, value []byte) error {
	path := hex.EncodeToString(key)
	if !bytes.Equal(valueHash, tools.SmtDigest(value)) {
		return fmt.Errorf("failed to verify value by valueHash(%s). key: %s", hex.EncodeToString(valueHash), hex.EncodeToString(key))
	}
	//return fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Set")
	domainNameState, err := BcsDeserializeDomainNameState(value)
	if err != nil {
		return err
	}
	domainNameSmtVal := &DomainNameSmtValue{
		Path:                          path,
		ValueHash:                     hex.EncodeToString(valueHash),
		Value:                         hex.EncodeToString(value),
		DomainNameIdTopLevelDomain:    domainNameState.DomainNameIdTopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameState.DomainNameIdSecondLevelDomain,
		ExpirationDate:                domainNameState.ExpirationDate,
		Owner:                         domainNameState.Owner,
	}
	err = m.db.db.Create(domainNameSmtVal).Error
	var mysqlErr *mysql.MySQLError
	if errors.As(err, &mysqlErr) && mysqlErr.Number == 1062 { // if it is Duplicate-entry DB error
		// oldData, err := m.Get(key)
		// if err != nil {
		// 	return err
		// }
		// if bytes.Equal(value, oldData) { // if it is really duplicate entry
		// 	return nil
		// } else {
		// 	return fmt.Errorf("reset value is not allowed, key: %s, value: %s, old value: %s", h, d, hex.EncodeToString(oldData))
		// }
		return nil
	}
	return err
}

// Update Starcoin height handled
func (w *MySqlDB) UpdateStarcoinHeight(h uint64) error {
	ch := &ChainHeight{
		Key:    KEY_STARCOIN_HEIGHT,
		Height: h,
	}
	return createOrUpdate(w.db, ch)
}

func (w *MySqlDB) GetStarcoinHeight() (uint64, error) {
	ch := ChainHeight{}
	if err := w.db.Where(&ChainHeight{
		Key: KEY_STARCOIN_HEIGHT,
	}).First(&ch).Error; err != nil {
		if !errors.Is(err, gorm.ErrRecordNotFound) {
			return 0, err
		} else {
			//fmt.Println("errors.Is(err, gorm.ErrRecordNotFound)")
			return 0, nil
		}
	}
	return ch.Height, nil
}

func createOrUpdate(db *gorm.DB, dest interface{}) error {
	if err := db.Save(dest).Error; err != nil {
		if !errors.Is(err, gorm.ErrRecordNotFound) {
			return err
		} else {
			return db.Create(dest).Error
		}
	}
	return nil
}
