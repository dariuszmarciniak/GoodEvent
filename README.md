# GoodEvent

## Application developed using Kotlin language for the client-side and Spring technology for the server-side.

### A mobile application that allows you to create accounts and then allows users to view events that can be filtered by location, name or date of the event.

![registration](https://user-images.githubusercontent.com/75750207/234877452-de78ca32-ef3c-427c-a9e6-75a46b2ddb46.png)
![menu](https://user-images.githubusercontent.com/75750207/234877637-b731693a-1b14-4d3d-abe7-5a48be057606.png)

![list](https://user-images.githubusercontent.com/75750207/234878195-cc516754-e5e7-4521-86bd-9afda09e24c3.png)
![list2](https://user-images.githubusercontent.com/75750207/234878207-c241d1c4-2982-4f8e-ab0a-ef7d439ce137.png)


Application users have the option of liking a given event or adding their own.

![details](https://user-images.githubusercontent.com/75750207/234878550-a1c74c6b-bb5f-4da9-90c2-2a3519fd774c.png)
![add](https://user-images.githubusercontent.com/75750207/234878518-06245b63-04c0-4340-a685-4fb22e8669ee.png)

In the details of the event there is a link to the location shown on google maps.

![locationn](https://user-images.githubusercontent.com/75750207/234878700-b70e0e6e-f376-43d1-ac32-d8459e9b817d.png)


All data is sent to a server made in Spring, then processed there and saved in the mysql database.
All information transferred from the client to the server is secured with Spring Security and the jwt token.
