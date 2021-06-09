package common.sql;

public enum Commands {
    // Asset Type Commands
    ADD_ASSET,
    GET_ASSET,
    DELETE_ASSET,
    GET_ASSET_SIZE,
    GET_ASSET_NAME_SET,

    // Organisation Commands
    ADD_ORG,
    ADD_CREDITS,
    REMOVE_CREDITS,
    ADD_QUANTITY,
    REMOVE_QUANTITY,
    GET_ORG,
    DELETE_ORG,
    GET_ORG_SIZE,
    GET_ORG_NAME_SET,

    // User Commands
    ADD_USER,
    UPDATE_PASSWORD,
    GET_USER,
    DELETE_USER,
    GET_USER_SIZE,
    GET_USER_NAME_SET,

    // Current Trade Commands
    ADD_TRADE,
    GET_BUY_SELL,
    GET_ORGTRADE,
    DELETE_TRADE,
    GET_TRADE_SIZE,
    GET_TRADE_NAME_SET,

    // Trade History Commands
    ADD_TRADE_HISTORY,
    GET_TRADE_ASSETS,
    GET_TRADE_HISTORY_SIZE,
    GET_TRADE_HISTORY_ID_SET
}
