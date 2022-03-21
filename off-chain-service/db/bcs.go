package db

import (
	"fmt"

	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/bcs"
	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/serde"
)

func (obj *DomainNameState) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := obj.GetDomainNameId().Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeU64(obj.ExpirationDate); err != nil {
		return err
	}
	domain_name_state_owner, err := obj.GetOwner()
	if err != nil {
		return err
	}
	var domain_name_state_owner_as_account_address AccountAddress = domain_name_state_owner
	if err := (&domain_name_state_owner_as_account_address).Serialize(serializer); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *DomainNameState) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeDomainNameState(deserializer serde.Deserializer) (DomainNameState, error) {
	var obj DomainNameState
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return obj, err
	}
	if val, err := DeserializeDomainNameId(deserializer); err == nil {
		obj.SetDomainNameId(&val)
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeU64(); err == nil {
		obj.ExpirationDate = val
	} else {
		return obj, err
	}
	if val, err := deserialize_array16_u8_array(deserializer); err == nil {
		obj.SetOwner(val)
	} else {
		return obj, err
	}
	deserializer.DecreaseContainerDepth()
	return obj, nil
}

func BcsDeserializeDomainNameState(input []byte) (DomainNameState, error) {
	if input == nil {
		var obj DomainNameState
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeDomainNameState(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}

func (obj *DomainNameId) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := serializer.SerializeBytes([]byte(obj.TopLevelDomain)); err != nil {
		return err
	}
	if err := serializer.SerializeBytes([]byte(obj.SecondLevelDomain)); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *DomainNameId) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeDomainNameId(deserializer serde.Deserializer) (DomainNameId, error) {
	var obj DomainNameId
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.TopLevelDomain = string(val)
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.SecondLevelDomain = string(val)
	} else {
		return obj, err
	}
	deserializer.DecreaseContainerDepth()
	return obj, nil
}

func BcsDeserializeDomainNameId(input []byte) (DomainNameId, error) {
	if input == nil {
		var obj DomainNameId
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeDomainNameId(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}

type AccountAddress [16]uint8

func (obj *AccountAddress) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := serialize_array16_u8_array((([16]uint8)(*obj)), serializer); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *AccountAddress) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeAccountAddress(deserializer serde.Deserializer) (AccountAddress, error) {
	var obj [16]uint8
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return (AccountAddress)(obj), err
	}
	if val, err := deserialize_array16_u8_array(deserializer); err == nil {
		obj = val
	} else {
		return ((AccountAddress)(obj)), err
	}
	deserializer.DecreaseContainerDepth()
	return (AccountAddress)(obj), nil
}

func BcsDeserializeAccountAddress(input []byte) (AccountAddress, error) {
	if input == nil {
		var obj AccountAddress
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeAccountAddress(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}

func serialize_array16_u8_array(value [16]uint8, serializer serde.Serializer) error {
	for _, item := range value {
		if err := serializer.SerializeU8(item); err != nil {
			return err
		}
	}
	return nil
}

func deserialize_array16_u8_array(deserializer serde.Deserializer) ([16]uint8, error) {
	var obj [16]uint8
	for i := range obj {
		if val, err := deserializer.DeserializeU8(); err == nil {
			obj[i] = val
		} else {
			return obj, err
		}
	}
	return obj, nil
}

type TokenCode struct {
	Address AccountAddress
	Module  string
	Name    string
}

func (obj *TokenCode) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := obj.Address.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeStr(obj.Module); err != nil {
		return err
	}
	if err := serializer.SerializeStr(obj.Name); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *TokenCode) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeTokenCode(deserializer serde.Deserializer) (TokenCode, error) {
	var obj TokenCode
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return obj, err
	}
	if val, err := DeserializeAccountAddress(deserializer); err == nil {
		obj.Address = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeStr(); err == nil {
		obj.Module = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeStr(); err == nil {
		obj.Name = val
	} else {
		return obj, err
	}
	deserializer.DecreaseContainerDepth()
	return obj, nil
}

func BcsDeserializeTokenCode(input []byte) (TokenCode, error) {
	if input == nil {
		var obj TokenCode
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeTokenCode(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}
