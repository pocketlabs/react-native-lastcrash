require 'json'

package = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |spec|
  spec.name         = "react-native-lastcrash"
  spec.version      = package['version']
  spec.summary      = package['description']
  spec.homepage     = package['homepage']
  spec.license      = package['license']
  spec.author       = package['author']
  spec.platform     = :ios, "13.0"
  spec.source       = { :git => package['repository']['url'], :tag => "#{spec.version}" }

  spec.source_files = "ios/**/*.{h,m,mm,swift}"
  spec.requires_arc = true

  spec.dependency "React-Core"
  spec.dependency "LastCrash", "~> 2.0.0"
end
