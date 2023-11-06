
### re-frame.dev.api

Functional documentation of the re-frame.dev.api isomorphic namespace

---

##### [README](../../../../README.md) > [DOCUMENTATION](../../../COVER.md) > re-frame.dev.api

### Index

- [event-handler-registered?](#event-handler-registered)

- [get-db-reset-count](#get-db-reset-count)

- [get-event-handler](#get-event-handler)

- [get-event-handlers](#get-event-handlers)

- [quit-debug-mode!](#quit-debug-mode)

- [set-debug-mode!](#set-debug-mode)

- [toggle-debug-mode!](#toggle-debug-mode)

---

### event-handler-registered?

```
@description
Returns TRUE if a registered event handler is found by the given 'event-kind' and 'event-id' values.
```

```
@param (keyword) event-kind
:cofx, :event, :fx, :sub
@param (keyword) event-id
```

```
@usage
(event-handler-registered? :sub :my-subscription)
```

```
@return (function)
```

<details>
<summary>Source code</summary>

```
(defn event-handler-registered?
  [event-kind event-id]
  (-> (get-event-handler event-kind event-id)
      (some?)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [event-handler-registered?]]))

(re-frame.dev.api/event-handler-registered? ...)
(event-handler-registered?                  ...)
```

</details>

---

### get-db-reset-count

```
@description
- Returns the actual Re-Frame DB write count.
- It only counts the DB writes when this function is subscribed!
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn get-db-reset-count
  [db _]
  (swap! state/DB-RESET-COUNT inc)
  (->   @state/DB-RESET-COUNT))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [get-db-reset-count]]))

(re-frame.dev.api/get-db-reset-count)
(get-db-reset-count)
```

</details>

---

### get-event-handler

```
@description
Returns a registered event handler found by the given 'event-kind' and 'event-id' values.
```

```
@param (keyword) event-kind
:cofx, :event, :fx, :sub
@param (keyword) event-id
```

```
@usage
(get-event-handler :sub :my-subscription)
```

```
@return (maps in list)
```

<details>
<summary>Source code</summary>

```
(defn get-event-handler
  [event-kind event-id]
  (-> (get-event-handlers)
      (get-in [event-kind event-id])))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [get-event-handler]]))

(re-frame.dev.api/get-event-handler ...)
(get-event-handler                  ...)
```

</details>

---

### get-event-handlers

```
@description
Returns the registered event handlers optionally filtered by the given 'event-kind' value.
```

```
@param (keyword)(opt) event-kind
:cofx, :event, :fx, :sub
```

```
@usage
(get-event-handlers)
```

```
@usage
(get-event-handlers :sub)
```

```
@example
(get-event-handlers)
=>
{:cofx  {...}
 :event {...}
 :fx    {...}
 :sub   {...}}
```

```
@example
(get-event-handlers :sub)
=>
{...}
```

```
@return (map)
{:cofx (map)
 :event (map)
 :fx (map)
 :sub (map)}
```

<details>
<summary>Source code</summary>

```
(defn get-event-handlers
  ([]                       (deref registrar/kind->id->handler))
  ([event-kind] (event-kind (deref registrar/kind->id->handler))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [get-event-handlers]]))

(re-frame.dev.api/get-event-handlers ...)
(get-event-handlers                  ...)
```

</details>

---

### quit-debug-mode!

```
@description
Turns the debug mode off.
```

```
@usage
(quit-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn quit-debug-mode!
  []
  (reset! state/DEBUG-MODE? false))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [quit-debug-mode!]]))

(re-frame.dev.api/quit-debug-mode!)
(quit-debug-mode!)
```

</details>

---

### set-debug-mode!

```
@description
Turns the debug mode on.
```

```
@usage
(set-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn set-debug-mode!
  []
  (reset! state/DEBUG-MODE? true))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [set-debug-mode!]]))

(re-frame.dev.api/set-debug-mode!)
(set-debug-mode!)
```

</details>

---

### toggle-debug-mode!

```
@description
Toggles the debug mode on/off.
```

```
@usage
(toggle-debug-mode!)
```

<details>
<summary>Source code</summary>

```
(defn toggle-debug-mode!
  []
  (swap! state/DEBUG-MODE? not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.dev.api :refer [toggle-debug-mode!]]))

(re-frame.dev.api/toggle-debug-mode!)
(toggle-debug-mode!)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

