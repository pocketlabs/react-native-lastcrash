# React Native LastCrash SDK

A React Native SDK for LastCrash - a comprehensive crash reporting and analytics solution that captures screenshots, monitors app state, and uploads crash reports with visual context.

## Features

- **Screenshot Capture**: Automatically captures screenshots at regular intervals
- **Crash Reporting**: Integrates with native crash reporting (KSCrash on iOS)
- **Network Monitoring**: Tracks network requests and responses
- **Event Tracking**: Custom event tracking and analytics
- **ANR Detection**: Application Not Responding detection
- **Freeze Detection**: Detects UI freezes and performance issues
- **Cross-Platform**: Works on both iOS and Android

## Installation

```sh
npm install react-native-lastcrash
# or
yarn add react-native-lastcrash
```

### iOS
After installing, run:

```sh
npx pod-install
```

That’s it! **No need to edit your Podfile.**

### Android
No additional steps required—**autolinking will handle everything**.

---

> **Note:**
> If you are using a monorepo, custom Podfile, or an older version of React Native (<0.60), see the troubleshooting section below.

---

## Usage

### Basic Setup

```typescript
import LastCrash from 'react-native-lastcrash';

// Configure LastCrash with your API key
LastCrash.configure('your-api-key-here');

// Set up crash delegate (optional)
LastCrash.setCrashReportSenderDelegate();

// Mark app as initialized
LastCrash.applicationInitialized();
```

### App.js Example

```typescript
import React, { useEffect } from 'react';
import { View, Text, Button } from 'react-native';
import LastCrash from 'react-native-lastcrash';

const App = () => {
  useEffect(() => {
    // Configure LastCrash
    LastCrash.configure('your-api-key-here');

    // Set up crash delegate
    LastCrash.setCrashReportSenderDelegate();

    // Mark app as initialized
    LastCrash.applicationInitialized();

    // Enable logging (iOS only)
    LastCrash.enableLogging();

    // Track app open event
    LastCrash.event('app_open');
  }, []);

  const handlePause = () => {
    LastCrash.pause();
    console.log('Video capture paused');
  };

  const handleUnpause = () => {
    LastCrash.unpause();
    console.log('Video capture resumed');
  };

  const handleCustomEvent = () => {
    LastCrash.event('button_clicked', 'test_button');
  };

  const handleCrash = () => {
    // Simulate a crash for testing
    throw new Error('Test crash');
  };

  return (
    <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
      <Text>LastCrash Demo</Text>
      <Button title="Pause Capture" onPress={handlePause} />
      <Button title="Resume Capture" onPress={handleUnpause} />
      <Button title="Track Event" onPress={handleCustomEvent} />
      <Button title="Test Crash" onPress={handleCrash} />
    </View>
  );
};

export default App;
```

### Error Boundary Integration

```typescript
import React from 'react';
import { View, Text } from 'react-native';
import LastCrash from 'react-native-lastcrash';

class ErrorBoundary extends React.Component {
  constructor(props) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError(error) {
    return { hasError: true };
  }

  componentDidCatch(error, errorInfo) {
    // Report error to LastCrash
    // (implement JS error reporting if needed)
  }

  render() {
    if (this.state.hasError) {
      return (
        <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text>Something went wrong.</Text>
        </View>
      );
    }

    return this.props.children;
  }
}

// Wrap your app with the error boundary
const AppWithErrorBoundary = () => (
  <ErrorBoundary>
    <App />
  </ErrorBoundary>
);
```

## API Reference

### Methods

#### `configure(apiKey: string)`
Configure LastCrash with your API key.

#### `setCrashReportSenderDelegate()`
Set up a delegate to handle crash callbacks.

#### `enableLogging()` / `disableLogging()`
Enable or disable LastCrash logging (iOS only).

#### `pause()` / `unpause()`
Pause or resume screenshot capture.

#### `sendCrashes()`
Manually send crash reports.

#### `event(name: string, value?: string)`
Track a custom event.

#### `applicationInitialized()`
Mark the application as initialized.

#### `addNetworkTrackingToDefaultSession()`
Add network tracking to the default URL session (iOS only).

#### `addMaskView(viewTag: number)`
Mask a specific view.

#### `removeMaskView(viewTag: number)`
Remove a mask from a specific view.

#### `removeAllMaskViews()`
Remove all view masks.

#### `addMaskRect(x: number, y: number, width: number, height: number, maskId: string)`
Mask a specific rectangle.

#### `removeMaskRect(maskId: string)`
Remove a specific rectangle mask.

#### `removeAllMaskRects()`
Remove all rectangle masks.

### Events

#### `LastCrashDidCrash`
Emitted when a crash is detected.

```typescript
import { DeviceEventEmitter } from 'react-native';

DeviceEventEmitter.addListener('LastCrashDidCrash', (reports) => {
  console.log('Crash detected:', reports);
});
```

## Troubleshooting

### Monorepos, Custom Podfiles, or Old React Native (<0.60)
- If your Podfile does **not** include `use_native_modules!`, or you are using a monorepo, you may need to manually add the pod to your Podfile:
  ```ruby
  pod 'react-native-lastcrash', :path => '../node_modules/react-native-lastcrash'
  ```
- Then run `npx pod-install` again.
- For Android, if autolinking does not work, add the project manually to `settings.gradle` and `build.gradle` as described in the React Native docs.

### Debug Mode

Enable logging to see detailed information (iOS only):

```typescript
LastCrash.enableLogging();
```

## Platform Differences

### iOS
- Uses KSCrash for native crash detection
- Screenshot capture uses CADisplayLink for smooth performance
- Network tracking uses custom NSURLProtocol

### Android
- Uses Android's native crash reporting
- Screenshot capture uses ViewTreeObserver
- Network tracking uses OkHttp interceptors

## License

MIT License - see LICENSE file for details.

## Support

For support, please contact support@lastcrash.io or visit our documentation at https://docs.lastcrash.io.

---
--- REMOVE THIS BEFORE PUBLISHIING
---
# 🛠️ Local Development & Testing for a React Native Package

This guide explains how to test your React Native package locally before publishing to npm.

---

## 1. Make Your Changes

Edit your package code as needed.

---

## 2. Build Your Package

If you use TypeScript or a build step, run:

```sh
npm run build
```

---

## 3. Create a Tarball

From your package directory, run:

```sh
npm pack
```

This will create a file like:

react-native-lastcrash-1.0.0.tgz


---

## 4. Install the Tarball in Your Test App

From your test app directory, run:

```sh
npm install /absolute/path/to/react-native-lastcrash/react-native-lastcrash-1.0.0.tgz
# or
yarn add /absolute/path/to/react-native-lastcrash/react-native-lastcrash-1.0.0.tgz
```

---

## 5. Rebuild Your Test App

- **iOS:**
  ```sh
  npx pod-install
  ```
- **Android:**
  Just rebuild/restart the app.

---

## 6. Repeat as Needed

Each time you make changes to your package:

1. `npm run build`
2. `npm pack`
3. Reinstall the new tarball in your test app
4. Rebuild your test app

---

## 📝 Alternative: Local Path Dependency

In your test app’s `package.json`:

```json
"dependencies": {
  "react-native-lastcrash": "file:../path/to/react-native-lastcrash"
}
```

Then run:

```sh
npm install
# or
yarn install
```

> **Note:** You’ll need to rebuild and reinstall after changes, and this may not exactly match the published package.

---

## ⚡️ Quick Reference Table

| Step         | Command/Action                                              |
|--------------|------------------------------------------------------------|
| Build        | `npm run build`                                            |
| Pack         | `npm pack`                                                 |
| Install      | `npm install /abs/path/to/package-1.0.0.tgz`               |
| iOS Rebuild  | `npx pod-install`                                          |
| Android      | Rebuild/restart app                                        |

---

**Tip:**
Automate these steps with a script for faster iteration if you’re making lots of changes!
