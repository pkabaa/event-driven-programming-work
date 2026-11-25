# Event-Driven System Design

## System Name: Smart Home Controller

## Part 1: Event Sources
1. `lightsToggle` (`JToggleButton`) - Generates `ActionEvent` when toggled ON or OFF.
2. `securityToggle` (`JToggleButton`) - Generates `ActionEvent` when armed or disarmed.
3. `doorButton` (`JButton`) - Generates `ActionEvent` simulating a front-door magnetic sensor trigger.
4. `statusLabel` (`JLabel`) - Displays real-time system state updates.
5. `eventLog` (`JTextArea`) - Logs timestamped system events chronologically.

## Part 2: Events
| Event Name | Source | Description |
| :--- | :--- | :--- |
| `LightsToggledEvent` | `lightsToggle` | Triggered when the user clicks the Lights toggle button |
| `SecurityToggledEvent` | `securityToggle` | Triggered when the user arms or disarms the security system |
| `DoorOpenedEvent` | `doorButton` | Triggered when the front door sensor detects the door opening |

## Part 3: Event Handlers
| Event | Handler Action |
| :--- | :--- |
| `LightsToggledEvent` | Updates button text (`ON`/`OFF`), updates status label, and appends timestamped entry to `eventLog` |
| `SecurityToggledEvent` | Updates button text (`ARMED`/`OFF`), resets background color if disarmed, updates status, and logs event |
| `DoorOpenedEvent` | Checks if security is armed; if armed, triggers red background alarm and logs intruder alert; otherwise logs normal door opening |

## Part 4: Event Flow Diagram
User Click → Event Source (`JButton`/`JToggleButton`) → `ActionEvent` Object → Swing Event Queue → Event Dispatch Thread (EDT) Loop → Registered `ActionListener` Handler → GUI & Log Update

## Part 5: Potential Issues
1. **Blocking the EDT with network/IoT calls** - Offload hardware communication to a background `Thread` and update UI via `SwingUtilities.invokeLater()`.
2. **Thread race conditions when updating the log** - Ensure all `JTextArea` appends execute strictly on the EDT.
3. **Unresponsive UI if alarm flashes via `Thread.sleep()`** - Use a non-blocking `javax.swing.Timer` instead of `Thread.sleep()` for visual alarm effects.