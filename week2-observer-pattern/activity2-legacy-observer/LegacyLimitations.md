# Limitations of java.util.Observer and java.util.Observable

## Limitation 1: Single Inheritance
Explain: Why is it a problem that Observable is a CLASS, not an INTERFACE?
Your answer: Because Java only supports single inheritance for classes, extending `Observable` prevents your subject class from extending any other superclass. If your class already needs to inherit behavior from another base class in your application, you cannot make it an `Observable` without major workarounds like composition.

## Limitation 2: Cannot Change the Notification Order
Explain: In what order are observers notified? Can you control this?
Your answer: Observers are notified in an unspecified, internal order managed by `Observable`. You cannot control, prioritize, or customize the sequence in which observers receive updates, which becomes problematic if one observer depends on another observer processing an update first.

## Limitation 3: setChanged() is Confusing
Explain: Why must you call setChanged() before notifyObservers()?
Your answer: `Observable` uses an internal boolean `changed` flag to decide whether an actual state change occurred before broadcasting updates, and `setChanged()` is a `protected` method that sets this flag to `true`. Because it is easy to forget to call `setChanged()`—and because you cannot call it from outside a subclass—`notifyObservers()` will silently do nothing and observers will never be notified.

## Limitation 4: Pull Model by Default
Explain: How does the observer get the data? Is this efficient?
Your answer: By default, `notifyObservers()` passes `null` as the argument, forcing the observer to "pull" the data back from the `Observable` object using type-casting (e.g., `instanceof`) and multiple getter method calls. This is inefficient and creates tighter coupling because the observer must know the concrete type and internal getter methods of the subject rather than receiving the relevant data directly.

## Limitation 5: Deprecated Since Java 9
Explain: Why did Java deprecate these classes?
Your answer: Java officially deprecated `java.util.Observer` and `java.util.Observable` in Java 9 because the API is overly rigid, non-generic (relying on raw `Object` arguments), not properly extensible due to `protected` methods like `setChanged()`, and lacks modern concurrency and thread-safety guarantees. Modern Java applications are instead encouraged to use custom observer interfaces, `java.beans.PropertyChangeListener`, or reactive frameworks like `java.util.concurrent.Flow` and JavaFX properties.