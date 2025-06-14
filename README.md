# Email Assistant AI

 **Project Status: Under Construction** 

An intelligent email assistant powered by AI that helps you compose and reply to emails efficiently. This Chrome extension streamlines your email workflow by providing smart suggestions, automated responses, and intelligent email composition capabilities directly in your browser.

## Features (In Development)

- **Browser Integration**: Seamless integration with email clients through Chrome extension
- **Smart Email Composition**: AI-powered email writing assistance
- **Intelligent Reply Suggestions**: Automated response generation based on email content
- **Email Context Analysis**: Understanding email tone and intent for better responses
- **User-Friendly Interface**: Clean and intuitive extension interface

## Tech Stack

### Frontend (Chrome Extension)
- **React**: Modern JavaScript library for building user interfaces
- **Chrome Extension APIs**: Browser integration for email client access
- **Component-based architecture** for scalable UI development

### Backend
- **Kotlin**: Modern programming language for JVM
- **Spring Boot**: Comprehensive framework for building production-ready applications
- **RESTful API architecture** for seamless frontend-backend communication

## Installation

### Prerequisites
- Node.js (v14 or higher)
- npm or yarn
- Java 11 or higher
- Gradle

### Frontend (Chrome Extension) Setup
```bash
# Navigate to the extension directory
cd client

# Install dependencies
npm install

# Build the extension
npm run build

# Load the extension in Chrome:
# 1. Open Chrome and go to chrome://extensions/
# 2. Enable "Developer mode"
# 3. Click "Load unpacked" and select the build folder
```

### Backend Setup
```bash
# Navigate to the backend directory
cd server

# Build the project
./gradlew build

# Run the Spring Boot application
./gradlew bootRun
```

The application will be available at:
- Chrome Extension: Load unpacked extension in Chrome developer mode
- Backend API: http://localhost:8080
