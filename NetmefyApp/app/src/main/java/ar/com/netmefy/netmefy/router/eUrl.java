package ar.com.netmefy.netmefy.router;

/**
 * Created by fiok on 22/07/2017.
 */

public enum eUrl {
    LOGIN,
    WIFI_GET_SSID,
    WIFI_GET_PASSWORD,
    WIFI_SET_SSID,
    WIFI_SET_PASSWORD,
    RESTART,
    LIST_CONNECTED,
    WIFI_SET_SSID_TO_GET_SESSIONKEY,
    WIFI_SET_PASSWORD_TO_GET_SESSIONKEY,
    RESTART_TO_GET_SESSIONKEY,
    ADD_BLOCK_BY_MAC,
    ADD_BLOCK_BY_MAC_TO_GET_SESSIONKEY,
    LOGOUT,
    REMOVE_BLOCK_BY_MAC,
    REMOVE_BLOCK_BY_MAC_TO_GET_SESSIONKEY,
    GET_MAC_LIST_BLOCKED,
    GET_URL_LIST_BLOCKED,
    ADD_BLOCK_BY_URL,
    ADD_BLOCK_BY_URL_TO_GET_SESSIONKEY,
    REMOVE_BLOCK_BY_URL,
    REMOVE_BLOCK_BY_URL_TO_GET_SESSIONKEY,
    LIST_CONNECTED_WIRELESS_STATICS,
    ADD_BLOCK_BY_URL_RULE,
    REMOVE_BLOCK_BY_URL_RULE,
    GET_URL_LIST_BLOCKED_RULE

}
