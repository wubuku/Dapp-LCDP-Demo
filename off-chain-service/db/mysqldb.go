package db

import (
	"github.com/celestiaorg/smt"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/schema"
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
	db.Set("gorm:table_options", "CHARSET=latin1").AutoMigrate(&DomainNameSmtNode{}, &DomainNameSmtValue{}, &DomainNameEvent{}, &DomainNameState{}, &DomainNameStateHead{})

	w := new(MySqlDB)
	w.db = db
	return w, nil
}

func (db *MySqlDB) NewDomainNameSmtNodeMapStore() (*smt.MapStore, error) {
	return nil, nil // todo
}
