# WeatherAppUpdate

WeatherAppUpdate is an Android application that provides real-time weather information based on the user's location or city input. The app fetches weather data using an external weather API and presents the information in a user-friendly interface.

## 🌦️ Features

- **Current Weather Data**: Displays the current temperature, humidity, wind speed, and weather description
- **City Search**: Users can search for weather by city name
- **Responsive Design**: Optimized for both phone and tablet screens
- **Unit Selection**: Option to toggle between metric (Celsius) and imperial (Fahrenheit) units
- **Error Handling**: Gracefully handles errors such as invalid city names or API issues

## 🛠️ Technologies Used

### Programming Language
- Kotlin

### Libraries
- Retrofit2 (Networking)
- Gson (JSON Parsing)
- Glide (Image Loading)

### API
- OpenWeatherMap API

## 📋 Prerequisites

Before running the app, ensure you have:
- Android Studio
- JDK (Java Development Kit)
- Physical or virtual Android device for testing

## 🚀 Installation

### 1. Clone the Repository
```bash
git clone https://github.com/satdevkumar01/WeaterAppUpdate.git
```

### 2. Open in Android Studio
- Launch Android Studio
- Select "Open an Existing Project"
- Navigate to the cloned project directory

### 3. Set Up API Key
1. Sign up at [OpenWeatherMap](https://openweathermap.org/)
2. Generate an API key
3. Replace the placeholder in `Constants.kt`:
```kotlin
const val API_KEY = "YOUR_API_KEY"
```

### 4. Run the App
- Connect an Android device or start an emulator
- Click "Run" in Android Studio

## 🌍 How to Use

1. Open the app
2. Search for a city by entering its name
3. View automatic location-based weather (ensure location permissions are granted)
4. Toggle between Celsius and Fahrenheit units

## 🔧 Troubleshooting

### Common Issues
- **"City not found"**: 
  - Check city name spelling
  - Verify city exists in OpenWeatherMap database
- **API Key Problems**:
  - Confirm API key is correctly added
  - Ensure key is valid and not expired
- **Location Issues**:
  - Grant location permissions
  - Ensure GPS is enabled
  - Check emulator location settings

## 🚧 Potential Enhancements

- Multiple city search
- Extended weather forecast
- Dark mode support
- Improved error messaging
- Weather data update animations

## 🤝 Contributing

1. Fork the repository
2. Create a new branch
```bash
git checkout -b feature-branch
```
3. Commit your changes
```bash
git commit -am 'Add new feature'
```
4. Push to your branch
```bash
git push origin feature-branch
```
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License. See the LICENSE file for details.

## 📞 Contact

For any queries or support, please open an issue in the GitHub repository.
