module sui_demo_contracts::player_delete_logic {
    use sui::tx_context::TxContext;
    use sui_demo_contracts::player;

    friend sui_demo_contracts::player_aggregate;

    public(friend) fun verify(
        player: &player::Player,
        ctx: &TxContext,
    ): player::PlayerDeleted {
        let _ = ctx;
        player::new_player_deleted(
            player,
        )
    }

    public(friend) fun mutate(
        player_deleted: &player::PlayerDeleted,
        player: player::Player,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): player::Player {
        let player_id = player::player_id(&player);
        let _ = ctx;
        let _ = player_id;
        let _ = player_deleted;
        player
    }

}
