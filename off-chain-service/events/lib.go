package events

import (
	"fmt"

	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/bcs"
	"github.com/novifinancial/serde-reflection/serde-generate/runtime/golang/serde"
)

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

type DomainNameId struct {
	TopLevelDomain    []byte
	SecondLevelDomain []byte
}

func (obj *DomainNameId) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.TopLevelDomain); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.SecondLevelDomain); err != nil {
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
		obj.TopLevelDomain = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.SecondLevelDomain = val
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

type DomainNameRegistered struct {
	DomainNameId       DomainNameId
	Owner              AccountAddress
	RegistrationPeriod uint64
	UpdatedState       DomainNameState
	UpdatedSmtRoot     []byte
	PreviousSmtRoot    []byte
}

func (obj *DomainNameRegistered) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := obj.DomainNameId.Serialize(serializer); err != nil {
		return err
	}
	if err := obj.Owner.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeU64(obj.RegistrationPeriod); err != nil {
		return err
	}
	if err := obj.UpdatedState.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.UpdatedSmtRoot); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.PreviousSmtRoot); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *DomainNameRegistered) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeDomainNameRegistered(deserializer serde.Deserializer) (DomainNameRegistered, error) {
	var obj DomainNameRegistered
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return obj, err
	}
	if val, err := DeserializeDomainNameId(deserializer); err == nil {
		obj.DomainNameId = val
	} else {
		return obj, err
	}
	if val, err := DeserializeAccountAddress(deserializer); err == nil {
		obj.Owner = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeU64(); err == nil {
		obj.RegistrationPeriod = val
	} else {
		return obj, err
	}
	if val, err := DeserializeDomainNameState(deserializer); err == nil {
		obj.UpdatedState = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.UpdatedSmtRoot = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.PreviousSmtRoot = val
	} else {
		return obj, err
	}
	deserializer.DecreaseContainerDepth()
	return obj, nil
}

func BcsDeserializeDomainNameRegistered(input []byte) (DomainNameRegistered, error) {
	if input == nil {
		var obj DomainNameRegistered
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeDomainNameRegistered(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}

type DomainNameRenewed struct {
	DomainNameId    DomainNameId
	Account         AccountAddress
	RenewPeriod     uint64
	UpdatedState    DomainNameState
	UpdatedSmtRoot  []byte
	PreviousSmtRoot []byte
}

func (obj *DomainNameRenewed) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := obj.DomainNameId.Serialize(serializer); err != nil {
		return err
	}
	if err := obj.Account.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeU64(obj.RenewPeriod); err != nil {
		return err
	}
	if err := obj.UpdatedState.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.UpdatedSmtRoot); err != nil {
		return err
	}
	if err := serializer.SerializeBytes(obj.PreviousSmtRoot); err != nil {
		return err
	}
	serializer.DecreaseContainerDepth()
	return nil
}

func (obj *DomainNameRenewed) BcsSerialize() ([]byte, error) {
	if obj == nil {
		return nil, fmt.Errorf("Cannot serialize null object")
	}
	serializer := bcs.NewSerializer()
	if err := obj.Serialize(serializer); err != nil {
		return nil, err
	}
	return serializer.GetBytes(), nil
}

func DeserializeDomainNameRenewed(deserializer serde.Deserializer) (DomainNameRenewed, error) {
	var obj DomainNameRenewed
	if err := deserializer.IncreaseContainerDepth(); err != nil {
		return obj, err
	}
	if val, err := DeserializeDomainNameId(deserializer); err == nil {
		obj.DomainNameId = val
	} else {
		return obj, err
	}
	if val, err := DeserializeAccountAddress(deserializer); err == nil {
		obj.Account = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeU64(); err == nil {
		obj.RenewPeriod = val
	} else {
		return obj, err
	}
	if val, err := DeserializeDomainNameState(deserializer); err == nil {
		obj.UpdatedState = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.UpdatedSmtRoot = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeBytes(); err == nil {
		obj.PreviousSmtRoot = val
	} else {
		return obj, err
	}
	deserializer.DecreaseContainerDepth()
	return obj, nil
}

func BcsDeserializeDomainNameRenewed(input []byte) (DomainNameRenewed, error) {
	if input == nil {
		var obj DomainNameRenewed
		return obj, fmt.Errorf("Cannot deserialize null array")
	}
	deserializer := bcs.NewDeserializer(input)
	obj, err := DeserializeDomainNameRenewed(deserializer)
	if err == nil && deserializer.GetBufferOffset() < uint64(len(input)) {
		return obj, fmt.Errorf("Some input bytes were not read")
	}
	return obj, err
}

type DomainNameState struct {
	DomainNameId   DomainNameId
	ExpirationDate uint64
	Owner          AccountAddress
}

func (obj *DomainNameState) Serialize(serializer serde.Serializer) error {
	if err := serializer.IncreaseContainerDepth(); err != nil {
		return err
	}
	if err := obj.DomainNameId.Serialize(serializer); err != nil {
		return err
	}
	if err := serializer.SerializeU64(obj.ExpirationDate); err != nil {
		return err
	}
	if err := obj.Owner.Serialize(serializer); err != nil {
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
		obj.DomainNameId = val
	} else {
		return obj, err
	}
	if val, err := deserializer.DeserializeU64(); err == nil {
		obj.ExpirationDate = val
	} else {
		return obj, err
	}
	if val, err := DeserializeAccountAddress(deserializer); err == nil {
		obj.Owner = val
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
