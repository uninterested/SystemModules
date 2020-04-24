"use strict";
exports.__esModule = true;
var react_native_1 = require("react-native");
var SystemModules = react_native_1.NativeModules.SystemModules;
var SystemModule = /** @class */ (function () {
    function SystemModule() {
    }
    /**
     * 获取当前的语言
     */
    SystemModule.getLanguage = function () {
        return this.language;
    };
    /**
     * 设置语言
     * @param newLanguage 新语言
     */
    SystemModule.setLanguage = function (newLanguage) {
        if (newLanguage === this.language)
            return;
        this.language = newLanguage;
        var next;
        if (newLanguage === 'EN') {
            next = 'en';
        }
        else if (newLanguage === 'ZHHans') {
            next = 'zh-hans';
        }
        else {
            next = 'zh-hant';
        }
        SystemModules.setLanguage(next);
    };
    /**
     * 转换语言
     */
    SystemModule.parseLanguage = function () {
        var lan = SystemModules.language || '';
        if (lan)
            return 'ZHHans';
        if (lan.indexOf('zh-cn') > -1)
            return 'ZHHans';
        if (lan.indexOf('zh-tw') > -1)
            return 'ZHHant';
        if (lan.indexOf('zh-hk') > -1)
            return 'ZHHant';
        if (lan.indexOf('zh-hans') > -1)
            return 'ZHHans';
        if (lan.indexOf('zh-hant') > -1)
            return 'ZHHant';
        if (lan.indexOf('en-') > -1)
            return 'EN';
        return 'EN';
    };
    /**
     * 顶部安全区域
     */
    SystemModule.safeTop = SystemModules.safeTop << 0;
    /**
     * 底部安全区域 (安卓是虚拟键的高度)
     */
    SystemModule.safeBottom = SystemModules.safeBottom << 0;
    /**
     * 宽
     */
    SystemModule.width = SystemModules.width << 0;
    /**
     * 高度
     */
    SystemModule.height = SystemModules.height << 0;
    /**
     * 系统版本
     */
    SystemModule.iOS = SystemModules.os === 'ios';
    /**
     * 页面底部边距
     */
    SystemModule.pageOffsetBottom = SystemModule.iOS ? SystemModule.safeBottom : 0;
    /**
     * 语言
     */
    SystemModule.language = SystemModule.parseLanguage();
    return SystemModule;
}());
exports["default"] = SystemModule;
