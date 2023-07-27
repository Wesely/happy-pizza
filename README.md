## Setup
<img width="1089" alt="image" src="https://github.com/Wesely/happy-pizza/assets/5109822/8b1dfba8-6d03-467b-894f-6ce69498e958">

設定中的 Gradle 需把 Java 版本設為 17

## Download Debug(Unsigned) APK
<img width="191" alt="image" src="https://github.com/Wesely/happy-pizza/assets/5109822/7d13eb0a-416d-4620-91d7-4fdc716bdd5d">

Link : https://drive.google.com/file/d/1M5_JyZiYmjelnZTzfn4CSvowiVwDhhYm/view?usp=drive_link

## Extra Logic
<img width="302" alt="image" src="https://github.com/Wesely/happy-pizza/assets/5109822/0af78992-2af8-449b-8035-61b4ac3064fe">

- 若 pm2.5 欄位為空，顯示為 "無資料"，並且會被當作最大整數來對待
- 以 pm2.5 大於中位數的觀測站資料放在上方水平 `RecyclerView`，其他放在下方垂直 `RecyclerView`
- 搜尋時會延遲1秒等待user輸入所有文字避免運算資源浪費
- 由於資料每小時更新，並且資料在本地端都有存檔，在下一個整點之前 `fetchAndStoreAirQualityData(force:Boolean)` 不會從網路取得資料除非 `force=true`

# Project Management

可以在 "Project" 當中找到所有 Issue (Task)
<img width="949" alt="image" src="https://github.com/Wesely/happy-pizza/assets/5109822/9c864b52-566d-43a1-904f-50b28dde498b">
