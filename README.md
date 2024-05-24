# Product Catalog

MVP application to enhance the management of client's product catalog and enable product data exports for online advertising purposes.

## Prerequisites
Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 11 or higher
- Apache Maven
- Docker

## Clone Repository

```bash
git clone https://github.com/danielphelan/product-catalog-manager.git
cd product-catalog-manager
```

## Usage

### Build and run locally

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```
Use your relevant API testing tool to call endpoints detailed below through http://localhost:8080.

### Pull and Deploy Docker Image
```bash
docker pull danp574/product-catalog-manager
```
```bash
docker run -p 10000:10000 danp574/product-catalog-manager
```
Use your relevant API testing tool to call endpoints detailed below through http://localhost:10000.

### Test Deployed Web Service
Application is currently deployed to a free service called Render. And testing can be completed using the below API endpoint, if needed.

**Please note, as this is a free service, it may take 1 to 2 minutes for the application to start up upon initial call.**


[https://product-catalog-manager.onrender.com/api/catalog/products](https://product-catalog-manager.onrender.com/api/catalog/products)


## Notes
- Original CSV is loaded by default but additional CSV's can be uploaded which overwrites all data
- sale_price and discount_value assumption - to operate and work with these fields, I added the ability to add a discount percentage to a product. This will be applied to the product across all stores.
- Clear lack of tests; Would have liked more time to focus on adding these.
- Other focus given more time would be validation throughout the application.
- Secured endpoints required the header **x-api-key** with value of **daniel-and-kargo-app**
