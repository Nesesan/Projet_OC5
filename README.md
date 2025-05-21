# Yoga Studio Project


## 🧱 Tech Stack

- **Frontend**: Angular 14
- **Backend**: Java 11 (Spring Boot)
- **Database**: MySQL (production) / H2 (for testing)
- **Testing**: Postman, Mockoon, Jasmine, Karma, Jacoco, Cypress

## 🚀 Project Setup

### Clone the Repository


git clone : [https://github.com/OpenClassrooms-Student-Center/P5-Full-Stack-testing](https://github.com/Nesesan/Projet_OC5.git)
- cd yoga

## Frontend Setup (Angular)
### Install Dependencies

- npm install
 ### Launch Development Server
- npm run start
- The application will be available at http://localhost:4200/.

## Backend Setup (Java + Spring Boot)
- From the backend root directory:

- Build the project : mvn clean install
- Run tests: mvn test
- Generate code coverage report (Jacoco): mvn jacoco:report
- The report will be available at: target/site/jacoco/index.html

### Frontend Testing

#### End-to-End (E2E) Tests

- Run Cypress tests: npm run e2e

- Generate E2E test coverage: npm run e2e:coverage

- Coverage report is located at: front/coverage/lcov-report/index.html

- Run unit tests: npm run test

- Watch mode for development: npm run test:watch

### Resources

- Postman Collection
#### Import the collection from:

- ressources/postman/yoga.postman_collection.json

#### MySQL Setup

- The SQL script to create the database schema is located in:

- ressources/sql/script.sql

#### Default admin credentials:

- Email: yoga@studio.com

- Password: test!1234
