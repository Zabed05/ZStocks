# 📈 ZStocks

> A simple Android stock trading simulation app built with Java and Android Studio.

ZStocks allows users to create an account, view simulated stock prices and charts, buy and sell stocks, and manage their personal portfolio using Firebase.

## ✨ Features

- 🔐 Firebase Authentication
- 👤 User profile with name and balance
- 📊 Stock list using RecyclerView
- 📈 Stock details with interactive charts
- 💰 Buy and sell stocks
- 📁 Personal portfolio management
- ☁️ Portfolio data stored in Firebase Firestore
- 💵 Automatic balance updates after transactions
- 📱 Clean and beginner-friendly Android UI
- 📡 Simulated stock data — no external API required

## 🛠️ Technologies

- **Language:** Java
- **IDE:** Android Studio
- **UI:** XML
- **Authentication:** Firebase Authentication
- **Database:** Firebase Firestore
- **UI Components:** RecyclerView, CardView
- **Charts:** MPAndroidChart

## 📱 App Flow

```text
Login / Register
       ↓
     Home
       ↓
   Stock List
       ↓
  Stock Details
    ↙      ↘
  Buy      Sell
    ↘      ↙
    Portfolio
```

## ☁️ Firebase Structure

```text
users
└── userId
    ├── name
    ├── email
    ├── phone
    ├── balance
    │
    └── portfolio
        └── stockName
            └── quantity
```

## 📂 Project Structure

```text
app/
└── src/main/
    ├── java/com/zak/zstocks/
    │   ├── activities/
    │   ├── adapter/
    │   ├── model/
    │   ├── utils/
    │   └── data/
    │
    └── res/
        ├── layout/
        ├── drawable/
        ├── mipmap/
        └── values/
```

## ⚠️ Disclaimer

ZStocks is a **stock market simulation project** created for educational purposes.

Stock prices are simulated and do not represent real-time market prices. No real money or real stock trading is involved.

## 🚀 Future Improvements

- [ ] Real-time stock market API
- [ ] Live price updates
- [ ] Transaction history
- [ ] Profit & loss tracking
- [ ] Portfolio analytics
- [ ] Watchlist
- [ ] Stock search and filtering
- [ ] Improved UI/UX
- [ ] Enhanced Firebase security rules

## 👨‍💻 Developer

**Md Zabed Aktar Khan** (https://github.com/Zabed05)

---

⭐ If you find ZStocks useful, consider giving the repository a star!
