package com.jeesite.modules.storevideo.ovopark.api;

public class BaseRequest {
    private String _aid;
    private String _akey;
    private String _format;
    private String _mt;
    private String _requestMode;
    private String _sig;
    private String _sm;
    private String _timestamp;
    private String _version;

    public String get_aid() {
        return _aid;
    }

    public void set_aid(String _aid) {
        this._aid = _aid;
    }

    public String get_akey() {
        return _akey;
    }

    public void set_akey(String _akey) {
        this._akey = _akey;
    }

    public String get_format() {
        return _format;
    }

    public void set_format(String _format) {
        this._format = _format;
    }

    public String get_mt() {
        return _mt;
    }

    public void set_mt(String _mt) {
        this._mt = _mt;
    }

    public String get_requestMode() {
        return _requestMode;
    }

    public void set_requestMode(String _requestMode) {
        this._requestMode = _requestMode;
    }

    public String get_sig() {
        return _sig;
    }

    public void set_sig(String _sig) {
        this._sig = _sig;
    }

    public String get_sm() {
        return _sm;
    }

    public void set_sm(String _sm) {
        this._sm = _sm;
    }

    public String get_timestamp() {
        return _timestamp;
    }

    public void set_timestamp(String _timestamp) {
        this._timestamp = _timestamp;
    }

    public String get_version() {
        return _version;
    }

    public void set_version(String _version) {
        this._version = _version;
    }
}
