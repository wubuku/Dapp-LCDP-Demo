package db

import (
	"bytes"
	"encoding/hex"
	"errors"
	"fmt"

	"github.com/celestiaorg/smt"
	gomysql "github.com/go-sql-driver/mysql"
	"gorm.io/driver/mysql"
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
	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{
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
		&DomainNameState{},
		&DomainNameStateHead{},
	)

	w := new(MySqlDB)
	w.db = db
	return w, nil
}

func (w *MySqlDB) GetDomainNameSmtValue(path string, smtRoot string) (*DomainNameSmtValue, error) {
	v := new(DomainNameSmtValue)
	if err := w.db.Where(&DomainNameSmtValue{
		Path:    path,
		SmtRoot: smtRoot,
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

func (w *MySqlDB) SaveDomainNameEvent(e *DomainNameEvent) error {
	err := w.db.Save(e).Error
	var mysqlErr *gomysql.MySQLError
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

func (db *MySqlDB) NewDomainNameSmtNodeMapStore() (smt.MapStore, error) {
	return &DomainNameSmtNodeMapStore{
		db: db,
	}, nil
}

//Get(key []byte) ([]byte, error)     // Get gets the value for a key.
//Set(key []byte, value []byte) error // Set updates the value for a key.
//Delete(key []byte) error            // Delete deletes a key.

type DomainNameSmtNodeMapStore struct {
	db *MySqlDB
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
	var mysqlErr *gomysql.MySQLError
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

func (db *MySqlDB) NewDomainNameSmtValueMapStore() smt.MapStore {
	return &DomainNameSmtValueMapStore{
		db: db,
	}
}

// Get gets the value for a key(path).
func (m *DomainNameSmtValueMapStore) Get(key []byte) ([]byte, error) {
	path := hex.EncodeToString(key)
	smtRoot := "todo"
	// todo
	//return nil, fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Get")
	domainNameSmtValue, err := m.db.GetDomainNameSmtValue(path, smtRoot)
	if err != nil {
		return nil, err
	}
	return hex.DecodeString(domainNameSmtValue.Value)

}

// Set updates the value for a key(path).
func (m *DomainNameSmtValueMapStore) Set(key []byte, value []byte) error {
	path := hex.EncodeToString(key)
	// todo
	//return fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Set")
	domainNameState, err := BcsDeserializeDomainNameState(value)
	if err != nil {
		return err
	}
	domainNameSmtVal := &DomainNameSmtValue{
		Path:                          path,
		SmtRoot:                       "todo", //todo
		Value:                         hex.EncodeToString(value),
		DomainNameIdTopLevelDomain:    domainNameState.DomainNameIdTopLevelDomain,
		DomainNameIdSecondLevelDomain: domainNameState.DomainNameIdSecondLevelDomain,
		ExpirationDate:                domainNameState.ExpirationDate,
		Owner:                         domainNameState.Owner,
	}
	err = m.db.db.Create(domainNameSmtVal).Error
	var mysqlErr *gomysql.MySQLError
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

// Delete deletes a key.
func (m *DomainNameSmtValueMapStore) Delete(key []byte) error {
	path := hex.EncodeToString(key)
	_ = path
	//return fmt.Errorf("NOT IMPLEMENTED - (m *DomainNameSmtValueMapStore) Delete") //todo
	return nil
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
