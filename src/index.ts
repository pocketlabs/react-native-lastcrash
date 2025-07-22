import { NativeModules } from 'react-native';

const { LastCrashModule } = NativeModules;

class LastCrashNative {
  public isInitialized = false;

  /**
   * Initialize the native LastCrash module
   */
  public configure(apiKey: string): void {
    if (LastCrashModule) {
      try {
        LastCrashModule.configure(apiKey);
        this.isInitialized = true;
      } catch (error) {
        console.error('Failed to configure LastCrash:', error);
        throw error;
      }
    }
  }

  /**
   * Set the crash report sender delegate
   */
  public setCrashReportSenderDelegate(): void {
    if (this.isInitialized) {
      LastCrashModule.setCrashReportSenderDelegate();
    }
  }

  /**
   * Enable logging
   */
  public enableLogging(): void {
    if (this.isInitialized) {
      LastCrashModule.enableLogging();
    }
  }

  /**
   * Disable logging
   */
  public disableLogging(): void {
    if (this.isInitialized) {
      LastCrashModule.disableLogging();
    }
  }

  /**
   * Pause LastCrash
   */
  public pause(): void {
    if (this.isInitialized) {
      LastCrashModule.pause();
    }
  }

  /**
   * Unpause LastCrash
   */
  public unpause(): void {
    if (this.isInitialized) {
      LastCrashModule.unpause();
    }
  }

  /**
   * Send crashes
   */
  public sendCrashes(): void {
    if (this.isInitialized) {
      LastCrashModule.sendCrashes();
    }
  }

  /**
   * Track event
   */
  public event(name: string, value?: string): void {
    if (this.isInitialized) {
      LastCrashModule.event(name, value);
    }
  }

  /**
   * Mark application as initialized
   */
  public applicationInitialized(): void {
    if (this.isInitialized) {
      LastCrashModule.applicationInitialized();
    }
  }

  /**
   * Add network tracking
   */
  public addNetworkTrackingToDefaultSession(): void {
    if (this.isInitialized) {
      LastCrashModule.addNetworkTrackingToDefaultSession();
    }
  }

  /**
   * Add mask view for LastCrash
   */
  public addMaskView(viewTag: number): void {
    if (this.isInitialized) {
      LastCrashModule.addMaskView(viewTag);
    }
  }

  /**
   * Remove mask view for LastCrash
   */
  public removeMaskView(viewTag: number): void {
    if (this.isInitialized) {
      LastCrashModule.removeMaskView(viewTag);
    }
  }

  /**
   * Remove all mask views for LastCrash
   */
  public removeAllMaskViews(): void {
    if (this.isInitialized) {
      LastCrashModule.removeAllMaskViews();
    }
  }

  /**
   * Add mask rect for LastCrash
   */
  public addMaskRect(x: number, y: number, width: number, height: number, maskId: string): void {
    if (this.isInitialized) {
      LastCrashModule.addMaskRect(x, y, width, height, maskId);
    }
  }

  /**
   * Remove mask rect for LastCrash
   */
  public removeMaskRect(maskId: string): void {
    if (this.isInitialized) {
      LastCrashModule.removeMaskRect(maskId);
    }
  }

  /**
   * Remove all mask rects for LastCrash
   */
  public removeAllMaskRects(): void {
    if (this.isInitialized) {
      LastCrashModule.removeAllMaskRects();
    }
  }
}

// Create and export a singleton instance
const LastCrash = new LastCrashNative();
export default LastCrash;
