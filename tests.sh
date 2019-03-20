#!/usr/bin/env bash

echo "Starting tests"

sed -i '' 's/"moxyReflectorPackage", "com.serezhazp.twitterclient.presentation"/"moxyReflectorPackage", ""/g' presentation/build.gradle
sed -i '' 's/"com.serezhazp.twitterclient.presentation", "com.serezhazp.twitterclient.ui"/"com.serezhazp.twitterclient.ui"/g' app/src/main/java/com/serezhazp/twitterclient/TwitterClientApplication.kt

./gradlew clean test

echo "Tests finished"

sed -i '' 's/"moxyReflectorPackage", ""/"moxyReflectorPackage", "com.serezhazp.twitterclient.presentation"/g' presentation/build.gradle
sed -i '' 's/"com.serezhazp.twitterclient.ui"/"com.serezhazp.twitterclient.presentation", "com.serezhazp.twitterclient.ui"/g' app/src/main/java/com/serezhazp/twitterclient/TwitterClientApplication.kt

echo "Undo reflector changes"
