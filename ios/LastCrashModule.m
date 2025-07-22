#import "LastCrashModule.h"
#import <LastCrash/LastCrash.h>
#import <React/RCTUIManager.h>

@implementation LastCrashModule

RCT_EXPORT_MODULE();

- (NSArray<NSString *> *)supportedEvents {
  return @[@"LastCrashDidCrash"];
}

- (void)lastCrashReportSenderHandleCrash {
  [self sendEventWithName:@"LastCrashDidCrash" body:@{}];
}

RCT_EXPORT_METHOD(configure:(NSString *)apiKey) {
  [LastCrash configure:apiKey];
}

RCT_EXPORT_METHOD(setCrashReportSenderDelegate) {
  [LastCrash setCrashReportSenderDelegate:self];
}

RCT_EXPORT_METHOD(enableLogging) {
  [LastCrash enabledLogging];
}

RCT_EXPORT_METHOD(disableLogging) {
  [LastCrash disableLogging];
}

RCT_EXPORT_METHOD(pause) {
  [LastCrash pause];
}

RCT_EXPORT_METHOD(unpause) {
  [LastCrash unpause];
}

RCT_EXPORT_METHOD(sendCrashes) {
  [LastCrash sendCrashes];
}

RCT_EXPORT_METHOD(event:(NSString *)name
                  value:(NSString *)value
                  ) {
  if (value) {
    [LastCrash event:name value:value];
  } else {
    [LastCrash event:name];
  }
}

RCT_EXPORT_METHOD(applicationInitialized) {
  [LastCrash applicationInitialized];
}

RCT_EXPORT_METHOD(applicationForceTermination) {
  // no-op for iOS
}

RCT_EXPORT_METHOD(addNetworkTrackingToDefaultSession) {
  [LastCrash addNetworkTrackingToDefaultSession];
}

RCT_EXPORT_METHOD(networkEvent:(NSURLRequest *)request
                  response:(NSHTTPURLResponse *)response
                  duration:(CFAbsoluteTime)duration
                  taskErrorCode:(NSInteger)errorCode
                  cancelled:(BOOL)cancelled
                  requestBytes:(long)requestBytes
                  responseBytes:(long)responseBytes) {
  [LastCrash networkEvent:request response:response duration:duration taskErrorCode:errorCode cancelled:cancelled requestBytes:requestBytes responseBytes:responseBytes];
}

RCT_EXPORT_METHOD(addMaskRect:(double)x y:(double)y width:(double)width height:(double)height maskId:(NSString *)maskId) {
  CGRect rect = CGRectMake(x, y, width, height);
  [LastCrash addMaskRect:rect maskId:maskId];
}

RCT_EXPORT_METHOD(removeMaskRect:(NSString *)maskId) {
  [LastCrash removeMaskRect:maskId];
}

RCT_EXPORT_METHOD(removeAllMaskRects) {
  [LastCrash removeAllMaskRects];
}

RCT_EXPORT_METHOD(addMaskView:(NSNumber *)viewTag) {
  dispatch_async(dispatch_get_main_queue(), ^{
    UIView *view = [self.bridge.uiManager viewForReactTag:viewTag];
    if (view) {
      [LastCrash addMaskView:view];
      NSLog(@"Mask view added: %@", view);
    } else {
      NSLog(@"Mask view not found for tag: %@", viewTag);
    }
  });
}

RCT_EXPORT_METHOD(removeMaskView:(NSNumber *)viewTag) {
  dispatch_async(dispatch_get_main_queue(), ^{
    UIView *view = [self.bridge.uiManager viewForReactTag:viewTag];
    if (view) {
      [LastCrash removeMaskView:view];
      NSLog(@"Mask view removed: %@", view);
    } else {
      NSLog(@"Mask view not found for tag: %@", viewTag);
    }
  });
}

RCT_EXPORT_METHOD(removeAllMaskViews) {
  [LastCrash removeAllMaskViews];
}

@end
