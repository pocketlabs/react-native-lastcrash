import { DeviceEventEmitter, NativeModules, NativeEventEmitter, Platform } from 'react-native';

const { LastCrashModule } = NativeModules;

class LastCrashNative {
  /**
   * Initialize the native LastCrash module
   */
  public configure(apiKey: string): void {
    if (LastCrashModule) {
      try {
        LastCrashModule.configure(apiKey);
      } catch (error) {
        console.error('Failed to configure LastCrash:', error);
        throw error;
      }
    }
  }

  /**
   * Set the crash report sender delegate
   */
  public setCrashReportSenderListener(callback: () => void): void {
    if (LastCrashModule) {
      if (Platform.OS === 'ios') {
        LastCrashModule.setCrashReportSenderDelegate();
        const eventEmitter = new NativeEventEmitter(LastCrashModule);
        eventEmitter.addListener('LastCrashDidCrash', callback);
      } else {
        LastCrashModule.setCrashReportSenderListener();
        DeviceEventEmitter.addListener('LastCrashDidCrash', callback);
      }
    }
  }

  /**
   * Enable logging
   */
  public enableLogging(): void {
    if (LastCrashModule) {
      LastCrashModule.enableLogging();
    }
  }

  /**
   * Disable logging
   */
  public disableLogging(): void {
    if (LastCrashModule) {
      LastCrashModule.disableLogging();
    }
  }

  /**
   * Pause LastCrash
   */
  public pause(): void {
    if (LastCrashModule) {
      LastCrashModule.pause();
    }
  }

  /**
   * Unpause LastCrash
   */
  public unpause(): void {
    if (LastCrashModule) {
      LastCrashModule.unpause();
    }
  }

  /**
   * Send crashes
   */
  public sendCrashes(): void {
    if (LastCrashModule) {
      LastCrashModule.sendCrashes();
    }
  }

  /**
   * Track event
   */
  public event(name: string, value?: string): void {
    if (LastCrashModule) {
      LastCrashModule.event(name, value);
    }
  }

  /**
   * Mark application as initialized
   */
  public applicationInitialized(): void {
    if (LastCrashModule) {
      LastCrashModule.applicationInitialized();
    }
  }

  /**
   * Add network tracking
   */
  public addNetworkTrackingToDefaultSession(): void {
    if (LastCrashModule) {
      LastCrashModule.addNetworkTrackingToDefaultSession();
    }
  }

  /**
   * Add mask view for LastCrash
   */
  public addMaskView(viewTag: number): void {
    if (LastCrashModule) {
      LastCrashModule.addMaskView(viewTag);
    }
  }

  /**
   * Remove mask view for LastCrash
   */
  public removeMaskView(viewTag: number): void {
    if (LastCrashModule) {
      LastCrashModule.removeMaskView(viewTag);
    }
  }

  /**
   * Remove all mask views for LastCrash
   */
  public removeAllMaskViews(): void {
    if (LastCrashModule) {
      LastCrashModule.removeAllMaskViews();
    }
  }

  /**
   * Add mask rect for LastCrash
   */
  public addMaskRect(x: number, y: number, width: number, height: number, maskId: string): void {
    if (LastCrashModule) {
      LastCrashModule.addMaskRect(x, y, width, height, maskId);
    }
  }

  /**
   * Remove mask rect for LastCrash
   */
  public removeMaskRect(maskId: string): void {
    if (LastCrashModule) {
      LastCrashModule.removeMaskRect(maskId);
    }
  }

  /**
   * Remove all mask rects for LastCrash
   */
  public removeAllMaskRects(): void {
    if (LastCrashModule) {
      LastCrashModule.removeAllMaskRects();
    }
  }
}

// Create and export a singleton instance
const LastCrash = new LastCrashNative();
export default LastCrash;
