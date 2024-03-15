#[allow(unused_mut_parameter)]
module sui_demo_contracts::player_create_logic {
    use std::string::String;
    use sui::tx_context::TxContext;
    use sui_demo_contracts::player;

    friend sui_demo_contracts::player_aggregate;

    public(friend) fun verify(
        nickname: std::ascii::String,
        intro: String,
        player_id_table: &player::PlayerIdTable,
        ctx: &mut TxContext,
    ): player::PlayerCreated {
        let player_id = sui::tx_context::sender(ctx);
        player::asset_player_id_not_exists(player_id, player_id_table);
        player::new_player_created(
            player_id,
            nickname,
            intro,
        )
    }

    public(friend) fun mutate(
        player_created: &player::PlayerCreated,
        player_id_table: &mut player::PlayerIdTable,
        ctx: &mut TxContext,
    ): player::Player {
        let player_id = player::player_created_player_id(player_created);
        let nickname = player::player_created_nickname(player_created);
        let intro = player::player_created_intro(player_created);
        player::create_player(
            player_id,
            nickname,
            intro,
            player_id_table,
            ctx,
        )
    }

}
