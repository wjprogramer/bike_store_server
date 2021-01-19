# Data

## Table

- 目前 exposed 暫時不支援 cross-schema
    
    未來如果支援的話，要記得改

    ```kotlin
    object BrandTable: IntIdTable("production.brands") {}
    ```
  
    (`production`, `sales` 為 schema 名稱)
  