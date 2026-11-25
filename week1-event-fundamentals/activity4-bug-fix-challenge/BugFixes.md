# Bug Fix Documentation

## Bug 1: Blocking the EDT
- **Symptom:** UI freezes when button clicked
- **Cause:** Long operation (`Thread.sleep(5000)`) runs directly on the EDT
- **Fix:** Run operation in a background thread, update UI on EDT
- **Code Change:** Wrapped `Thread.sleep(5000)` in `new Thread(() -> { ... }).start()` and updated `statusLabel` inside `SwingUtilities.invokeLater(...)`.

## Bug 2: Missing Listener
- **Symptom:** Button click does nothing
- **Cause:** No `ActionListener` registered on `incrementButton`
- **Fix:** Added `ActionListener` to increment button
- **Code Change:** Added `incrementButton.addActionListener(e -> { count++; countLabel.setText("Count: " + count); });`.

## Bug 3: Wrong Thread
- **Symptom:** Erratic behavior, potential thread-safety crashes
- **Cause:** GUI component (`timeLabel`) updated directly from a background thread
- **Fix:** Used `SwingUtilities.invokeLater()` for GUI updates
- **Code Change:** Wrapped `timeLabel.setText(...)` inside `SwingUtilities.invokeLater(() -> timeLabel.setText(currentTime))`.

## Bug 4: Memory Leak
- **Symptom:** Memory usage grows over time even after buttons are removed
- **Cause:** References kept in `createdButtons` list after removal from UI
- **Fix:** Clear the list when removing buttons
- **Code Change:** Added `createdButtons.clear();` inside `removeButton`'s `ActionListener`.