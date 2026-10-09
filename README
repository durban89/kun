# 🎵 Kun Music

本地 + 网络音乐播放器（Android），深色霓虹「赛博迷幻」风格，支持 Google 账号登录。

## 技术栈

- Kotlin + Jetpack Compose（Material3，Compose BOM 2024.05.00）
- 播放：AndroidX Media3 ExoPlayer（1.9.0）
- 网络：Coil（封面加载）
- 数据：DataStore（本地设置/收藏），kotlinx-serialization
- 登录：Firebase Auth + Google Play Services Auth
- 构建：Gradle 8.13，AGP（应用 plugin），compileSdk 35 / targetSdk 33 / minSdk 24

## 功能

- 本地文件播放（MediaStore 授权，Android 13+ 单独申请音频/视频/图片权限）
- 网络文件播放（URL）
- 内置（bundled）曲目播放与收藏持久化
- 播放主界面：封面高斯模糊背景、歌词、渐变色进度条

## 构建

```bash
./gradlew :app:assembleDebug      # debug APK
./gradlew :app:assembleRelease    # release APK
```

产物位置：`app/build/outputs/apk/{debug,release}/`

## 签名（密钥不在仓库内）

发布签名密钥位于项目外 `~/keystores/kun/`，随私有 dotfiles 仓库同步恢复：

```
~/keystores/kun/
├── release.jks                 # JKS 签名文件（alias: kun）
├── keystore.properties         # storeFile/storePassword/keyAlias/keyPassword
└── google-services.json        # Firebase 配置
```

- `app/build.gradle` 通过 `System.getProperty('user.home')` 读取 `~/keystores/kun/keystore.properties`；文件缺失时自动回退为**未签名**构建。
- debug 与 release 共用同一把签名（同一指纹），Google 登录无需分别绑定。
- `google-services.json` 不在仓库内，构建时由 `copyGoogleServices` 任务自动从 `~/keystores/kun/` 拷贝到 `app/google-services.json`（该插件只认此固定路径）。

## Firebase / Google 登录

在 Firebase 控制台注册应用 SHA-1（release 指纹）后重新下载 `google-services.json` 放入 `~/keystores/kun/` 即可。

## 分支

默认分支：`main`（原 `master` 已废弃）。服务器部署清单 `repos.txt` 已同步指向 `main`。

---

# 🎵 「赛博迷幻流派」视觉设计规范

本方案采用全暗黑沉浸式背景，搭配极具呼吸感与流动感的霓虹渐变色。视觉上能够营造出深夜 Livehouse、电音派对的氛围，完美契合流行、电子、嘻哈或独立音乐的调性，同时能最大程度突出色彩斑斓的专辑封面。

---

## 🎨 核心调色盘 (Color Palette)

### 1. 基础背景色
*   **主背景 (Background)**
    *   **HEX:** `#0D0E15`
    *   **用途:** 页面全局背景。采用深邃的极暗蓝黑，比纯黑更有质感，提供极致的深夜沉浸感。
*   **卡片/高亮背景 (Surface)**
    *   **HEX:** `#1A1C29`
    *   **用途:** 用于歌曲列表卡片、弹窗、二级菜单。拉开界面前后的空间层级。

### 2. 品牌特征色
*   **品牌主色 (Primary)**
    *   **HEX:** `#FF2A54`
    *   **用途:** 电音霓虹红。代表热情、律动。用于播放键、核心标签、Logo、选中状态。
*   **品牌辅助色 (Secondary)**
    *   **HEX:** `#00F5D4`
    *   **用途:** 极光薄荷绿。与主色形成微撞色，用于进度条已播放部分、音效波动动画。

### 3. 文本与内容色
*   **主文字 (Text Primary)**
    *   **HEX:** `#FFFFFF`
    *   **用途:** 纯白。用于歌名、大标题、重要提示，确保在暗色背景下有极佳的清晰度。
*   **次文字 (Text Secondary)**
    *   **HEX:** `#8E9AA7`
    *   **用途:** 雾霾蓝灰。用于歌手名、专辑名、播放时间、未选中菜单，降低视觉疲劳。

---

## 🛠 界面应用实例与布局规范

### 1. 播放主界面 (Player View)
*   **背景层:** 获取当前播放歌曲的专辑封面，进行 **70% 黑色遮罩 + 40px 高斯模糊** 动态处理，让背景随音乐风格实时流动。
*   **控制键:** 播放、下一首等主控制键采用纯白 (`#FFFFFF`)，“喜欢”红心图标被激活时使用品牌主色 (`#FF2A54`)。
*   **进度条 (Progress Bar):** 已播放部分使用从辅助色 (`#00F5D4`) 到主色 (`#FF2A54`) 的渐变色，未播放部分使用背景高亮色 (`#1A1C29`)。

### 2. 发现/首页 (Discover)
*   **整体基调:** 全暗黑背景 (`#0D0E15`) 沉衬托，让彩色的 Banner 与歌单卡片像夜空中的霓虹灯一样吸睛。
*   **金刚位/标签栏:** 采用浅色线性图标，默认状态为次文字色 (`#8E9AA7`)，仅在选中状态下渲染为品牌主色 (`#FF2A54`)。

### 3. 动态歌词 (Lyrics View)
*   **未播放歌词:** 次要文字色 (`#8E9AA7`)，标准字号，常规字重。
*   **当前正播放歌词:** 字号放大并加粗，颜色变为纯白 (`#FFFFFF`)，可配合轻微的 1~2px 霓虹发光动效。