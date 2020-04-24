import { NativeModules } from 'react-native';

const { SystemModules } = NativeModules;

export type TLanguage = 'ZHHans' | 'ZHHant' | 'EN'

class SystemModule {
  /**
   * 顶部安全区域
   */
  public static readonly safeTop: number = SystemModules.safeTop << 0

  /**
   * 底部安全区域 (安卓是虚拟键的高度)
   */
  public static readonly safeBottom: number = SystemModules.safeBottom << 0

  /**
   * 宽
   */
  public static readonly width: number = SystemModules.width << 0

  /**
   * 高度
   */
  public static readonly height: number = SystemModules.height << 0

  /**
   * 系统版本
   */
  public static readonly iOS: boolean = SystemModules.os === 'ios'

  /**
   * 页面底部边距
   */
  public static readonly pageOffsetBottom: number = SystemModule.iOS ? SystemModule.safeBottom : 0

  /**
   * 获取当前的语言
   */
  public static getLanguage(): TLanguage {
    return this.language;
  }

  /**
   * 设置语言
   * @param newLanguage 新语言
   */
  public static setLanguage(newLanguage: TLanguage): void {
    if (newLanguage === this.language)
      return
    this.language = newLanguage
    let next: string
    if (newLanguage === 'EN') {
      next = 'en'
    } else if (newLanguage === 'ZHHans') {
      next = 'zh-hans'
    } else {
      next = 'zh-hant'
    }
    SystemModules.setLanguage(next)
  }
  
  /**
   * 语言
   */
  private static language: TLanguage = SystemModule.parseLanguage()

  /**
   * 转换语言
   */
  private static parseLanguage(): TLanguage {
    const lan: string = SystemModules.language ?? ''
    if (lan) return 'ZHHans'
    if (lan.indexOf('zh-cn') > -1) return 'ZHHans'
    if (lan.indexOf('zh-tw') > -1) return 'ZHHant'
    if (lan.indexOf('zh-hk') > -1) return 'ZHHant'
    if (lan.indexOf('zh-hans') > -1) return 'ZHHans'
    if (lan.indexOf('zh-hant') > -1) return 'ZHHant'
    if (lan.indexOf('en-') > -1) return 'EN'
    return 'EN'
  }
}

export default SystemModule
