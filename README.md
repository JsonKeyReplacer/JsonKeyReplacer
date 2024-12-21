# JsonKeyReplacer

## origin json 

```json
{
"id": "12345",
"name": "Example JSON Structure with Dynamic Keys",
"metadata": {
"created_at": "2023-10-01T12:34:56Z",
"updated_at": "2023-10-05T18:22:33Z",
"version": 3
},
"dynamic_data": [
{
"type": "user",
"user_id": "user1",
"name": "John Doe",
"email": "john.doe@example.com",
"age": 30,
"address": {
"street": "123 Main St",
"city": "New York",
"country": "USA"
}
},
{
"type": "product",
"product_id": "prod1",
"name": "Laptop",
"price": 999.99,
"attributes": {
"brand": "ExampleBrand",
"model": "X123",
"specs": {
"cpu": "Intel i7",
"ram": "16GB",
"storage": "512GB SSD"
}
},
"availability": {
"in_stock": true,
"quantity": 15
}
},
{
"type": "order",
"order_id": "order1",
"user_id": "user1",
"products": [
{
"product_id": "prod1",
"quantity": 1
},
{
"product_id": "prod2",
"quantity": 2
}
],
"total_price": 2399.97,
"payment_method": "credit_card",
"special_note": "Please deliver before 5 PM."
},
{
"type": "review",
"review_id": "rev1",
"product_id": "prod1",
"user_id": "user1",
"rating": 4.5,
"comment": "Great laptop, fast and reliable!",
"date": "2023-10-03",
"images": [
{
"url": "https://example.com/image1.jpg",
"caption": "Laptop on desk"
},
{
"url": "https://example.com/image2.jpg",
"caption": "Close-up of keyboard"
}
]
},
{
"type": "promotion",
"promotion_id": "promo1",
"name": "Holiday Sale",
"discount_percentage": 10,
"valid_for": ["prod1", "prod2"],
"valid_until": "2023-12-31",
"terms_and_conditions": "Offer valid for limited time only."
}
]
}
```

## use JsonKeyReplacer

```java
List<ReplaceRule> replaceRules = new ArrayList<>();
replaceRules.add(new ReplaceRule("$.dynamic_data[*].attributes", "attrs"));
replaceRules.add(new ReplaceRule("$.dynamic_data[*].images", "imgs"));
replaceRules.add(new ReplaceRule("$.dynamic_data[*].images[*].caption", "common"));

String modifiedJson = jsonKeyReplacer.modifyKeys(json1, replaceRules);
```

## modifiedJson

```json
{
"id": "12345",
"name": "Example JSON Structure with Dynamic Keys",
"metadata": {
"created_at": "2023-10-01T12:34:56Z",
"updated_at": "2023-10-05T18:22:33Z",
"version": 3
},
"dynamic_data": [
{
"type": "user",
"user_id": "user1",
"name": "John Doe",
"email": "john.doe@example.com",
"age": 30,
"address": {
"street": "123 Main St",
"city": "New York",
"country": "USA"
}
},
{
"type": "product",
"product_id": "prod1",
"name": "Laptop",
"price": 999.99,
"availability": {
"in_stock": true,
"quantity": 15
},
"attrs": {
"brand": "ExampleBrand",
"model": "X123",
"specs": {
"cpu": "Intel i7",
"ram": "16GB",
"storage": "512GB SSD"
}
}
},
{
"type": "order",
"order_id": "order1",
"user_id": "user1",
"products": [
{
"product_id": "prod1",
"quantity": 1
},
{
"product_id": "prod2",
"quantity": 2
}
],
"total_price": 2399.97,
"payment_method": "credit_card",
"special_note": "Please deliver before 5 PM."
},
{
"type": "review",
"review_id": "rev1",
"product_id": "prod1",
"user_id": "user1",
"rating": 4.5,
"comment": "Great laptop, fast and reliable!",
"date": "2023-10-03",
"imgs": [
{
"url": "https://example.com/image1.jpg",
"common": "Laptop on desk"
},
{
"url": "https://example.com/image2.jpg",
"common": "Close-up of keyboard"
}
]
},
{
"type": "promotion",
"promotion_id": "promo1",
"name": "Holiday Sale",
"discount_percentage": 10,
"valid_for": [
"prod1",
"prod2"
],
"valid_until": "2023-12-31",
"terms_and_conditions": "Offer valid for limited time only."
}
]
}
```