package main

import (
	"encoding/json"
	"net/http"
	"starcoin-ns-demo/db"
	"starcoin-ns-demo/tools"
	"starcoin-ns-demo/vo"

	"github.com/celestiaorg/smt"
	"github.com/gin-gonic/gin"
)

func getDomainNameStateAndSmtProof(c *gin.Context) {
	topLevelDomain := c.Query("top_level_domain")
	secondLevelDomain := c.Query("second_level_domain")
	smtRootQuery := c.DefaultQuery("smt_root", "")
	var state *db.DomainNameState
	var proof *smt.SparseMerkleProof
	var smtRoot []byte
	var err error
	if smtRootQuery == "" {
		state, proof, smtRoot, err = starcoinManager.GetDomainNameStateAndSmtProof(db.NewDomainNameId(topLevelDomain, secondLevelDomain))
	} else {
		state, proof, err = starcoinManager.GetDomainNameStateAndSmtProofForRoot(db.NewDomainNameId(topLevelDomain, secondLevelDomain), smtRoot)
		smtRoot, _ = tools.HexToBytes(smtRootQuery)
	}
	if err != nil {
		c.JSON(http.StatusInternalServerError, "GetDomainNameStateAndSmtProof error") //todo ???
		return
	}
	if proof == nil {
		c.JSON(http.StatusNotFound, "proof not found") //todo ???
		return
	}

	var voState *vo.DomainNameState
	if state != nil {
		voState = vo.NewDomainNameState(
			vo.NewDomainNameId(state.DomainNameIdTopLevelDomain, state.DomainNameIdSecondLevelDomain),
			state.ExpirationDate,
			tools.EncodeToHex(func() []byte { a, _ := state.GetOwner(); return a[:] }()),
		)
	}
	voStateAndProof := vo.NewDomainNameStateAndSmtProof(
		voState,
		vo.NewSparseMerkleProof(
			vo.BytesSliceToHexSlice(proof.SideNodes),
			tools.EncodeToHex(proof.NonMembershipLeafData),
		),
		tools.EncodeToHex(smtRoot),
	)
	c.JSON(http.StatusOK, voStateAndProof)
}

func getDomainNameState(c *gin.Context) {
	jsonId := c.Param("id")
	if jsonId == "" {
		c.JSON(http.StatusOK, nil)
		return
	}
	var idArray []string
	err := json.Unmarshal([]byte(jsonId), &idArray)
	if err != nil || len(idArray) != 2 {
		c.JSON(http.StatusInternalServerError, "Parse Id error") //todo ???
		return
	}
	topLevelDomain := idArray[0]    //c.Query("top_level_domain")
	secondLevelDomain := idArray[1] //c.Query("second_level_domain")
	s, err := starcoinManager.GetDB().GetDomainNameState(topLevelDomain, secondLevelDomain)
	if err != nil {
		c.JSON(http.StatusInternalServerError, "GetDomainNameState error") //todo ???
		return
	}
	c.JSON(http.StatusOK, s)
}

func getDomainNameStates(c *gin.Context) {
	ss, err := starcoinManager.GetDB().GetAllDomainNameState()
	if err != nil {
		c.JSON(http.StatusInternalServerError, "GetAllDomainNameState error") //todo ???
		return
	}
	c.JSON(http.StatusOK, ss)
}
