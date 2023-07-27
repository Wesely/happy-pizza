# ubiquiti-assignment

- 若 pm2.5 欄位為空，顯示為 "無資料"
- 以 pm2.5 大於中位數的觀測站資料放在上方水平 `RecyclerView`，其他放在下方垂直 `RecyclerView`
- 由於資料每小時更新，並且資料在本地端都有存檔，在下一個整點之前 `fetchAndStoreAirQualityData(force:Boolean)` 不會從網路取得資料除非 `force=true`