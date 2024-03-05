module sui_demo_contracts::player_update_logic {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_demo_contracts::player;

    friend sui_demo_contracts::player_aggregate;

    public(friend) fun verify(
        nickname: std::ascii::String,
        intro: String,
        player: &player::Player,
        ctx: &TxContext,
    ): player::PlayerUpdated {
        let _ = ctx;
        player::new_player_updated(
            player,
            nickname,
            intro,
        )
    }

    public(friend) fun mutate(
        player_updated: &player::PlayerUpdated,
        player: player::Player,
        ctx: &TxContext, // modify the reference to mutable if needed
    ): player::Player {
        let nickname = player::player_updated_nickname(player_updated);
        let intro = player::player_updated_intro(player_updated);
        let player_id = player::player_id(&player);
        let _ = ctx;
        let _ = player_id;
        player::set_nickname(&mut player, nickname);
        player::set_intro(&mut player, intro);
        player
    }

}
