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
		&DomainNameStateHead{},
	)

	//db.AutoMigrate(&DomainNameState{}) // just create as template

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

// ///////////////////// DomainNameEvent ///////////////////////

// Create(insert) DomainNameEvent
func (w *MySqlDB) CreateDomainNameEvent(e *DomainNameEvent) error {
	err := w.db.Create(e).Error
	var mysqlErr *mysql.MySQLError
	if errors.As(err, &mysqlErr) && mysqlErr.Number == 1062 { // if it is Duplicate-entry DB error
		existedE, err := w.GetDomainNameEventByBlockHashAndEventKey(e.BlockHash, e.EventKey)
		if err != nil {
			return err
		}
		if existedE != nil {
			// When chain is forked, we rollback to common ancester block and re-process it, so ignore this err?
			return nil
			// // can only re-create last DomainNameEvent?
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

// ///////////////////// DomainNameEventSequence ///////////////////////

// Get DomainNameEventSequence by sequenceId.
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

// Set DomainNameEventSequence's StateTableName and status to STATE_TABLE_BUILT
func (w *MySqlDB) SetDomainNameEventSequenceStateTableNameBuilt(es *DomainNameEventSequence, stateTableName string) error {
	es.StateTableName = stateTableName
	es.Status = DOMAIN_NAME_EVENT_SEQUENCE_STATUS_STATE_TABLE_BUILT
	err := w.db.Save(es).Error
	return err
}

// Reset DomainNameEventSequence's StateTableName and status to empty
func (w *MySqlDB) ResetDomainNameEventSequenceStateTableName(es *DomainNameEventSequence) error {
	es.StateTableName = ""
	es.Status = ""
	err := w.db.Save(es).Error
	return err
}

// ///////////////////// DomainNameStateHead and DomainNameState ///////////////////////

func (w *MySqlDB) UpdateDomainNameStateAndHeadForTableByEvent(tableName string, h *DomainNameStateHead, e *DomainNameEvent) error {
	err := w.db.Transaction(func(tx *gorm.DB) error {
		updatedStateOwner, err := e.GetUpdatedStateOwner()
		if err != nil {
			return err
		}
		err = CreateOrUpdateDomainNameStateForTable(tx, tableName, e.DomainNameIdTopLevelDomain, e.DomainNameIdSecondLevelDomain, e.UpdatedStateExpirationDate, updatedStateOwner, e.BlockNumber)
		if err != nil {
			return err
		}
		err = UpdateDomainNameStateHeadByEvent(tx, h, e)
		return err
	})
	return err
}

// ///////////////////// Operate DomainNameState using specific table name  /////////////////////////

const (
	SQL_FORMAT_SELECT_DOMAIN_NAME_STATE = `SELECT 
		domain_name_id_top_level_domain,
		domain_name_id_second_level_domain,
		expiration_date,
		owner,
		created_at_block_number,
		updated_at_block_number
	FROM
		%s s
	WHERE
		s.domain_name_id_top_level_domain = ?
			AND s.domain_name_id_second_level_domain = ?  limit 1
	`

	SQL_FORMAT_INSERT_DOMAIN_NAME_STATE = `INSERT INTO %s
	(domain_name_id_top_level_domain,
	domain_name_id_second_level_domain,
	expiration_date,
	owner,
	created_at_block_number,
	updated_at_block_number)
	VALUES
	(?,
	?,
	?,
	?,
	?,
	?)
	`

	SQL_FORMAT_UPDATE_DOMAIN_NAME_STATE = `UPDATE %s
	SET
	expiration_date = ?,
	owner = ?,
	updated_at_block_number = ?
	WHERE domain_name_id_top_level_domain = ? AND domain_name_id_second_level_domain = ?
	`

	SQL_FORMAT_CREATE_TABLE_DOMAIN_NAME_STATE = `
	CREATE TABLE %s (
		domain_name_id_top_level_domain VARCHAR(100) NOT NULL,
		domain_name_id_second_level_domain VARCHAR(100) NOT NULL,
		expiration_date BIGINT(20) UNSIGNED DEFAULT NULL,
		owner VARCHAR(66) DEFAULT NULL,
		created_at_block_number BIGINT(20) UNSIGNED NOT NULL,
		updated_at_block_number BIGINT(20) UNSIGNED NOT NULL,
		PRIMARY KEY (domain_name_id_top_level_domain , domain_name_id_second_level_domain),
		KEY idx_domain_name_state_updated_at_block_number (updated_at_block_number)
	)  ENGINE=INNODB DEFAULT CHARSET=LATIN1	
	`

	SQL_CREATE_OR_REPLACE_VIEW_DOMAIN_NAME_STATE = `
	CREATE OR REPLACE VIEW domain_name_state AS
    SELECT 
        *
    FROM
        %s;	
	`
)

// //////////////////// SQL formats end. //////////////////////

func (w *MySqlDB) CreateOrReplaceDomainNameStateView(tableName string) error {
	sql := fmt.Sprintf(SQL_CREATE_OR_REPLACE_VIEW_DOMAIN_NAME_STATE, tableName)
	return w.db.Exec(sql).Error
}

func (w *MySqlDB) UpdateDomainNameStateForTableByEvent(tableName string, e *DomainNameEvent) error {
	err := w.db.Transaction(func(tx *gorm.DB) error {
		updatedStateOwner, err := e.GetUpdatedStateOwner()
		if err != nil {
			return err
		}
		err = CreateOrUpdateDomainNameStateForTable(tx, tableName, e.DomainNameIdTopLevelDomain, e.DomainNameIdSecondLevelDomain, e.UpdatedStateExpirationDate, updatedStateOwner, e.BlockNumber)
		if err != nil {
			return err
		}
		return nil
	})
	return err
}

func CreateOrUpdateDomainNameStateForTable(tx *gorm.DB, tableName string,
	domainNameIdTopLevelDomain string,
	domainNameIdSecondLevelDomain string,
	updatedStateExpirationDate uint64,
	updatedStateOwner [16]uint8,
	updatedAtBlockNumber uint64,
) error {
	s, err := GetDomainNameStateForTable(tx, tableName, domainNameIdTopLevelDomain, domainNameIdSecondLevelDomain)
	if err != nil {
		return err
	}
	if s == nil {
		err := InsertDomainNameStateForTable(tx, tableName,
			domainNameIdTopLevelDomain, domainNameIdSecondLevelDomain,
			updatedStateExpirationDate, updatedStateOwner,
			updatedAtBlockNumber,
		)
		if err != nil {
			return err
		}
	} else {
		err := UpdateDomainNameStateForTable(tx, tableName,
			domainNameIdTopLevelDomain, domainNameIdSecondLevelDomain,
			updatedStateExpirationDate, updatedStateOwner,
			updatedAtBlockNumber,
		)
		if err != nil {
			return err
		}
	}
	return nil
}

func GetDomainNameStateForTable(database *gorm.DB, tableName string, tld string, sld string) (*DomainNameState, error) {
	var list []DomainNameState
	sql := fmt.Sprintf(SQL_FORMAT_SELECT_DOMAIN_NAME_STATE, tableName)
	err := database.Raw(sql, tld, sld).Scan(&list).Error
	if err != nil {
		return nil, err
	}
	if len(list) == 0 {
		return nil, nil
	}
	first := list[0]
	return &first, nil
}

func InsertDomainNameStateForTable(tx *gorm.DB, tableName string,
	domainNameIdTopLevelDomain string,
	domainNameIdSecondLevelDomain string,
	updatedStateExpirationDate uint64,
	updatedStateOwner [16]uint8,
	updatedAtBlockNumber uint64,
) error {
	sql := fmt.Sprintf(SQL_FORMAT_INSERT_DOMAIN_NAME_STATE, tableName)
	err := tx.Exec(sql,
		domainNameIdTopLevelDomain,
		domainNameIdSecondLevelDomain,
		updatedStateExpirationDate,
		hex.EncodeToString(updatedStateOwner[:]),
		updatedAtBlockNumber,
		updatedAtBlockNumber,
	).Error
	return err
}

func UpdateDomainNameStateForTable(tx *gorm.DB, tableName string,
	domainNameIdTopLevelDomain string,
	domainNameIdSecondLevelDomain string,
	updatedStateExpirationDate uint64,
	updatedStateOwner [16]uint8,
	updatedAtBlockNumber uint64,
) error {
	sql := fmt.Sprintf(SQL_FORMAT_UPDATE_DOMAIN_NAME_STATE, tableName)
	err := tx.Exec(sql,
		updatedStateExpirationDate,
		hex.EncodeToString(updatedStateOwner[:]),
		updatedAtBlockNumber,
		domainNameIdTopLevelDomain,
		domainNameIdSecondLevelDomain,
	).Error
	return err
}

func (w *MySqlDB) CreateDomainNameStateTable(tableName string) error {
	sql := fmt.Sprintf(SQL_FORMAT_CREATE_TABLE_DOMAIN_NAME_STATE, tableName)
	return w.db.Exec(sql).Error
}

func (w *MySqlDB) GetAllDomainNameState() ([]*DomainNameState, error) {
	var database *gorm.DB = w.db
	var list []*DomainNameState
	err := database.Find(&list).Error
	if err != nil {
		return nil, err
	}
	return list, nil
}

func (w *MySqlDB) GetDomainNameState(domainNameIdTopLevelDomain string, domainNameIdSecondLevelDomain string) (*DomainNameState, error) {
	var database *gorm.DB = w.db
	var list []DomainNameState
	err := database.Where(&DomainNameState{
		DomainNameIdTopLevelDomain:    domainNameIdTopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameIdSecondLevelDomain,
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

// func (w *MySqlDB) UpdateDomainNameStateByEvent(e *DomainNameEvent) error {
// 	err := w.db.Transaction(func(tx *gorm.DB) error {
// 		updatedStateOwner, err := e.GetUpdatedStateOwner()
// 		if err != nil {
// 			return err
// 		}
// 		err = createOrUpdateDomainNameState(tx, e.DomainNameIdTopLevelDomain, e.DomainNameIdSecondLevelDomain, e.UpdatedStateExpirationDate, updatedStateOwner, e.BlockNumber)
// 		if err != nil {
// 			return err
// 		}
// 		err = w.createOrUpdateDomainNameStateHead(tx, e)
// 		return nil
// 	})
// 	return err
// }

// func createOrUpdateDomainNameState(tx *gorm.DB,
// 	domainNameIdTopLevelDomain string,
// 	domainNameIdSecondLevelDomain string,
// 	updatedStateExpirationDate uint64,
// 	updatedStateOwner [16]uint8,
// 	updatedAtBlockNumber uint64,
// ) error {
// 	s, err := getDomainNameState(tx, domainNameIdTopLevelDomain, domainNameIdSecondLevelDomain)
// 	if err != nil {
// 		return err
// 	}
// 	if s == nil {
// 		s = NewDomainNameState(NewDomainNameId(domainNameIdTopLevelDomain, domainNameIdSecondLevelDomain),
// 			updatedStateExpirationDate, updatedStateOwner)
// 		s.CreatedAtBlockNumber = updatedAtBlockNumber
// 		s.UpdatedAtBlockNumber = updatedAtBlockNumber
// 		if err = tx.Create(s).Error; err != nil {
// 			return err
// 		}
// 	} else {
// 		s.SetOwner(updatedStateOwner)
// 		s.UpdatedAtBlockNumber = updatedAtBlockNumber
// 		if err = tx.Save(s).Error; err != nil {
// 			return err
// 		}
// 	}
// 	return nil
// }
// ////////////////////////////////////////////

// ///////////////////// DomainNameStateHead ///////////////////////

// Create(insert) a new DomainNameStateHead which status is _INITIALIZING_.
func (w *MySqlDB) CreateDomainNameStateHead(headId string, smtRoot string, stateTableName string) (*DomainNameStateHead, error) {
	h := NewDomainNameStateHead(headId, "_INITIALIZING_", "_INITIALIZING_", smtRoot, stateTableName)
	if err := w.db.Create(h).Error; err != nil {
		return nil, err
	}
	return h, nil
}

// Create(insert) a New DomainNameStateHead by last handled event.
func (w *MySqlDB) CreateDomainNameStateHeadByEvent(headId string, e *DomainNameEvent, stateTableName string) error {
	h := NewDomainNameStateHead(
		headId,
		e.BlockHash,
		e.EventKey,
		e.UpdatedSmtRoot,
		stateTableName,
	)
	return w.db.Create(h).Error
}

func (w *MySqlDB) DeleteDomainNameStateHead(headId string) error {
	return w.db.Delete(&DomainNameStateHead{
		HeadId: headId,
	}).Error
}

func (w *MySqlDB) GetDefaultDomainNameStateHead() (*DomainNameStateHead, error) {
	return getDomainNameStateHead(w.db, DOMAIN_NAME_STATE_HEAD_ID_DEFAULT)
}

func (w *MySqlDB) GetDomainNameStateHead(headId string) (*DomainNameStateHead, error) {
	return getDomainNameStateHead(w.db, headId)
}

func UpdateDomainNameStateHeadByEvent(tx *gorm.DB, h *DomainNameStateHead, e *DomainNameEvent) error {
	h.BlockHash = e.BlockHash
	h.EventKey = e.EventKey
	h.SmtRoot = e.UpdatedSmtRoot
	dbUpdated := tx.Model(&DomainNameStateHead{}).Where(
		"head_id = ? and smt_root = ?", h.HeadId, e.PreviousSmtRoot,
	).Updates(h)
	if err := dbUpdated.Error; err != nil {
		return err
	}
	rowsAffected := dbUpdated.RowsAffected
	if rowsAffected == 0 {
		return fmt.Errorf("optimistic lock error. headId: %s, smtRoot: %s", h.HeadId, e.PreviousSmtRoot)
	}
	return nil
}

func getDomainNameStateHeadIdByEvent(e *DomainNameEvent) string {
	headId := DOMAIN_NAME_STATE_HEAD_ID_DEFAULT //todo
	return headId
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

// func (w *MySqlDB) createOrUpdateDomainNameStateHead(tx *gorm.DB, e *DomainNameEvent, stateTableName string) error {
// 	headId := getDomainNameStateHeadIdByEvent(e)
// 	h, err := getDomainNameStateHead(tx, headId)
// 	if err != nil {
// 		return err
// 	}
// 	if h == nil {
// 		h := NewDomainNameStateHead(headId, e.BlockHash, e.EventKey, e.UpdatedSmtRoot, stateTableName)
// 		if err = tx.Create(h).Error; err != nil {
// 			return err
// 		}
// 	} else {
// 		return UpdateDomainNameStateHeadByEvent(tx, h, e)
// 	}
// 	return nil
// }

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

// //////////////////////// util methods //////////////////////////

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

func (w *MySqlDB) HasTable(tableName string) bool {
	return w.db.Migrator().HasTable(tableName)
}

// func (w *MySqlDB) DropTable(tableName string) error {
// 	if err := w.db.Migrator().DropTable(tableName); err != nil {
// 		return err
// 	}
// 	return nil
// }

// func (w *MySqlDB) RenameTable(o string, n string) error {
// 	return w.db.Migrator().RenameTable(o, n)
// }
