package db

func localDevDB() (*MySqlDB, error) {
	//CREATE SCHEMA `starcoin_ns_demo` DEFAULT CHARACTER SET utf8mb4 ;
	var dsn string = "root:123456@tcp(127.0.0.1:3306)/starcoin_ns_demo?charset=utf8mb4&parseTime=True&loc=Local"
	db, err := NewMySqlDB(dsn)
	if err != nil {
		return nil, err
	}
	return db, nil
}
