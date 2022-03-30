package main

import (
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
