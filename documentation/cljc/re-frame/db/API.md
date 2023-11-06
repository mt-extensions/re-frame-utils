
### re-frame.db.api

Functional documentation of the re-frame.db.api isomorphic namespace

---

##### [README](../../../../README.md) > [DOCUMENTATION](../../../COVER.md) > re-frame.db.api

### Index

- [apply-item!](#apply-item)

- [copy-item!](#copy-item)

- [dec-item!](#dec-item)

- [dec-item-n!](#dec-item-n)

- [empty-db!](#empty-db)

- [get-applied-item](#get-applied-item)

- [get-db](#get-db)

- [get-item](#get-item)

- [get-item-count](#get-item-count)

- [inc-item!](#inc-item)

- [inc-item-n!](#inc-item-n)

- [item-exists?](#item-exists)

- [move-item!](#move-item)

- [remove-item!](#remove-item)

- [remove-item-n!](#remove-item-n)

- [remove-vector-item!](#remove-vector-item)

- [set-item!](#set-item)

- [set-vector-item!](#set-vector-item)

- [subscribe-item](#subscribe-item)

- [subscribed-item](#subscribed-item)

- [toggle-item!](#toggle-item)

- [toggle-item-value!](#toggle-item-value)

---

### apply-item!

```
@description
Applies the given 'f' function on the given 'item-path'.
```

```
@param (vector) item-path
@param (function) f
@param (list of *) params
```

```
@usage
(r apply-item! db [:my-item] not)
```

```
@example
(def db {:my-item false})
(r apply-item! db [:my-item] not)
=>
{:my-item true}
```

```
@example
(def db {:my-item [:pear]})
(r apply-item! db [:my-item] conj :apple)
=>
{:my-item [:pear :apple]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn apply-item!
  [db [_ item-path f & params]]
  (let [item   (get-in db item-path)
        params (cons item params)]
       (assoc-in db item-path (apply f params))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [apply-item!]]))

(re-frame.db.api/apply-item! ...)
(apply-item!                 ...)
```

</details>

---

### copy-item!

```
@description
Duplicates the item found on the given 'item-path' to the 'copy-path'.
```

```
@param (vector) item-path
@param (vector) copy-path
```

```
@usage
(r copy-item! db [:copy-from] [:copy-to])
```

```
@example
(def db {:copy-from :my-value})
(r copy-item! db [:copy-from] [:copy-to])
=>
{:copy-from :my-value
 :copy-to   :my-value}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn copy-item!
  [db [_ item-path copy-path]]
  (if-let [item (get-in db item-path)]
          (assoc-in  db copy-path item)
          (dissoc-in db copy-path)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [copy-item!]]))

(re-frame.db.api/copy-item! ...)
(copy-item!                 ...)
```

</details>

---

### dec-item!

```
@description
Decreases the value found on the given 'item-path' by one.
```

```
@param (vector) item-path
```

```
@usage
```

```
@example
(def db {:my-item 42})
(r inc-item! db [:my-item])
=>
{:my-item 41}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn dec-item!
  [db [_ item-path]]
  (update-in db item-path dec))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [dec-item!]]))

(re-frame.db.api/dec-item! ...)
(dec-item!                 ...)
```

</details>

---

### dec-item-n!

```
@description
Decreases the values found on the given 'item-paths' by one.
```

```
@param (vectors in vector) item-paths
```

```
@usage
(r dec-item-n! db [[:my-item] [:your-item]])
```

```
@example
(def db {:my-item 42 :your-item 69})
(r dec-item-n! db [[:my-item] [:your-item]])
=>
{:my-item 41 :your-item 68}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn dec-item-n!
  [db [_ & item-paths]]
  (letfn [(f [db item-path] (update-in db item-path dec))]
         (reduce f db item-paths)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [dec-item-n!]]))

(re-frame.db.api/dec-item-n! ...)
(dec-item-n!                 ...)
```

</details>

---

### empty-db!

```
@description
Returns an empty map.
```

```
@usage
(r empty-db! db)
```

```
@example
(def db {:my-item :my-value})
(r empty-db! db)
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn empty-db!
  [_ _]
  (-> {}))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [empty-db!]]))

(re-frame.db.api/empty-db!)
(empty-db!)
```

</details>

---

### get-applied-item

```
@param (vector) item-path
@param (function) f
@param (list of *) params
```

```
@usage
(r get-applied-item db [:my-item] inc)
```

```
@usage
(r get-applied-item db [:my-item] + 42)
```

```
@example
(def db {:my-item 42})
(r get-applied-item db [:my-item] inc)
=>
43
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn get-applied-item
  [db [_ item-path f & params]]
  (let [item   (get-in db item-path)
        params (cons item params)]
       (apply f params)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [get-applied-item]]))

(re-frame.db.api/get-applied-item ...)
(get-applied-item                 ...)
```

</details>

---

### get-db

```
@description
Returns the db.
```

```
@usage
(r get-db db)
```

```
@example
(def db {:my-item :my-value})
(r get-db db)
=>
{:my-item :my-value}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn get-db
  [db _]
  (-> db))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [get-db]]))

(re-frame.db.api/get-db)
(get-db)
```

</details>

---

### get-item

```
@param (vector) item-path
@param (*)(opt) default-value
```

```
@usage
(r get-item db [:my-item])
```

```
@usage
(r get-item db [:my-item] "Default value")
```

```
@example
(def db {:my-item :my-value})
(r get-item db [:my-item])
=>
:my-value
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn get-item
  [db [_ item-path default-value]]
  (get-in db item-path default-value))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [get-item]]))

(re-frame.db.api/get-item ...)
(get-item                 ...)
```

</details>

---

### get-item-count

```
@param (vector) item-path
```

```
@usage
(r get-item-count db [:my-item])
```

```
@example
(def db {:my-item [:a :b :c]})
(r get-item-count db [:my-item])
=>
3
```

```
@example
(def db {:my-item "My string"})
(r get-item-count db [:my-item])
=>
9
```

```
@return (integer)
```

<details>
<summary>Source code</summary>

```
(defn get-item-count
  [db [_ item-path]]
  (let [item (get-in db item-path)]
       (count item)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [get-item-count]]))

(re-frame.db.api/get-item-count ...)
(get-item-count                 ...)
```

</details>

---

### inc-item!

```
@description
Increases the value found on the given 'item-path' by one.
```

```
@param (vector) item-path
```

```
@usage
(r inc-item! db [:my-item])
```

```
@example
(def db {:my-item 42})
(r inc-item! db [:my-item])
=>
{:my-item 43}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn inc-item!
  [db [_ item-path]]
  (update-in db item-path inc))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [inc-item!]]))

(re-frame.db.api/inc-item! ...)
(inc-item!                 ...)
```

</details>

---

### inc-item-n!

```
@description
Increases the values found on the given 'item-paths' by one.
```

```
@param (vectors in vector) item-paths
```

```
@usage
(r inc-item-n! db [[:my-item] [:your-item]])
```

```
@example
(def db {:my-item 42 :your-item 69})
(r inc-item-n! db [[:my-item] [:your-item]])
=>
{:my-item 43 :your-item 70}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn inc-item-n!
  [db [_ & item-paths]]
  (letfn [(f [db item-path] (update-in db item-path inc))]
         (reduce f db item-paths)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [inc-item-n!]]))

(re-frame.db.api/inc-item-n! ...)
(inc-item-n!                 ...)
```

</details>

---

### item-exists?

```
@param (vector) item-path
```

```
@usage
(r item-exists? db [:my-item])
```

```
@example
(def db {:my-item :my-value})
(r item-exists? db [:my-item])
=>
true
```

```
@return (boolean)
```

<details>
<summary>Source code</summary>

```
(defn item-exists?
  [db [_ item-path]]
  (some? (get-in db item-path)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [item-exists?]]))

(re-frame.db.api/item-exists? ...)
(item-exists?                 ...)
```

</details>

---

### move-item!

```
@description
Moves the item found on the given 'item-path' to the 'destination-path'.
```

```
@param (vector) item-path
@param (vector) destination-path
```

```
@usage
(r move-item! db [:move-from] [:move-to])
```

```
@example
(def db {:move-from :my-value})
(r move-item! db [:move-from] [:move-to])
=>
{:move-to :my-value}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn move-item!
  [db [_ item-path destination-path]]
  (if-let [item (get-in db item-path)]
          (-> db (assoc-in  destination-path item)
                 (dissoc-in item-path))
          (dissoc-in db destination-path)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [move-item!]]))

(re-frame.db.api/move-item! ...)
(move-item!                 ...)
```

</details>

---

### remove-item!

```
@description
Removes the item from the given 'item-path'.
```

```
@param (vector) item-path
```

```
@usage
(r remove-item! db [:my-item])
```

```
@example
(def db {:my-item :my-value})
(r remove-item! db [:my-item])
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-item!
  [db [_ item-path]]
  (dissoc-in db item-path))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [remove-item!]]))

(re-frame.db.api/remove-item! ...)
(remove-item!                 ...)
```

</details>

---

### remove-item-n!

```
@description
Removes the items from the given 'item-paths'.
```

```
@param (vectors in vector) item-paths
```

```
@usage
(r remove-item-n! db [[:my-item] [:your-item]])
```

```
@example
(def db {:my-item :my-value :your-item :your-value})
(r remove-item-n! db [[:my-item] [:your-item]])
=>
{}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-item-n!
  [db [_ & item-paths]]
  (letfn [(f [db item-path] (dissoc-in db item-path))]
         (reduce f db item-paths)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [remove-item-n!]]))

(re-frame.db.api/remove-item-n! ...)
(remove-item-n!                 ...)
```

</details>

---

### remove-vector-item!

```
@warning
Last item in the given 'item-path' vector must be integer!
```

```
@description
Removes the item from the given 'item-path' that must be a vector and the item path must contain the item's index.
```

```
@param (vector) item-path
```

```
@usage
(r remove-vector-item! db [:my-item 0])
```

```
@example
(def db {:my-item [:a :b :c]})
(r remove-vector-item! db [:my-item 0])
=>
{:my-item [:b :c]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn remove-vector-item!
  [db [_ item-path]]
  (let [parent-path         (vector/remove-last-item item-path)
        item-dex            (vector/last-item        item-path)
        parent-item         (get-in db parent-path)
        updated-parent-item (vector/remove-nth-item parent-item item-dex)]
       (assoc-in db parent-path updated-parent-item)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [remove-vector-item!]]))

(re-frame.db.api/remove-vector-item! ...)
(remove-vector-item!                 ...)
```

</details>

---

### set-item!

```
@description
Writes the given 'item' to the given 'item-path'.
```

```
@param (vector) item-path
@param (*) item
```

```
@usage
(r set-item! db [:my-item] :my-value)
```

```
@example
(def db {})
(r set-item! db [:my-item] :my-value)
=>
{:my-item :my-value}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn set-item!
  [db [_ item-path item]]
  (if item (assoc-in  db item-path item)
           (dissoc-in db item-path)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [set-item!]]))

(re-frame.db.api/set-item! ...)
(set-item!                 ...)
```

</details>

---

### set-vector-item!

```
@warning
Last item in the given 'item-path' vector must be integer!
```

```
@description
- Writes the given 'item' to the given 'item-path' (that can be a vector) to a specific index.
- If the parent item ('item-path') is not a vector this function converts it to a vector with one item ('item').
```

```
@param (vector) item-path
@param (*) item
```

```
@usage
(r set-vector-item! db [:my-item 0] :item-value)
```

```
@example
(def db {})
(r set-vector-item! db [:my-item 0] :item-value)
=>
{:my-item [:item-value]}
```

```
@example
(def db {})
(r set-vector-item! db [:my-item 2] :item-value)
=>
{:my-item [:item-value]}
```

```
@example
(def db {:my-item {}})
(r set-vector-item! db [:my-item 0] :item-value)
=>
{:my-item [:item-value]}
```

```
@example
(def db {:my-item [])
(r set-vector-item! db [:my-item 0] :item-value)
=>
{:my-item [:item-value]}
```

```
@example
(def db {:my-item [:first-value :second-value])
(r set-vector-item! db [:my-item 0] :item-value)
=>
{:my-item [:item-value :second-value]}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn set-vector-item!
  [db [_ item-path item]]
  (let [item-parent-path (vector/remove-last-item item-path)
        item-dex         (vector/last-item        item-path)
        item-parent      (get-in db item-parent-path)]
       (if (vector/nonempty? item-parent)
           (let [updated-item-parent (vector/replace-nth-item item-parent item-dex item)]
                (assoc-in db item-parent-path updated-item-parent))
           (let [updated-item-parent [item]]
                (assoc-in db item-parent-path updated-item-parent)))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [set-vector-item!]]))

(re-frame.db.api/set-vector-item! ...)
(set-vector-item!                 ...)
```

</details>

---

### subscribe-item

```
@param (vector) item-path
@param (*)(opt) default-value
```

```
@usage
(subscribe-item [:my-item])
```

```
@return (atom)
```

<details>
<summary>Source code</summary>

```
(defn subscribe-item
  ([item-path]
   (-> [:get-item item-path] subscribe))

  ([item-path default-value]
   (-> [:get-item item-path default-value] subscribe)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [subscribe-item]]))

(re-frame.db.api/subscribe-item ...)
(subscribe-item                 ...)
```

</details>

---

### subscribed-item

```
@description
Returns the actual deref'ed value of a db item.
```

```
@param (vector) item-path
@param (*)(opt) default-value
```

```
@usage
(subscribed-item [:my-item])
```

```
@return (*)
```

<details>
<summary>Source code</summary>

```
(defn subscribed-item
  ([item-path]
   (-> [:get-item item-path] subscribe deref))

  ([item-path default-value]
   (-> [:get-item item-path default-value] subscribe deref)))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [subscribed-item]]))

(re-frame.db.api/subscribed-item ...)
(subscribed-item                 ...)
```

</details>

---

### toggle-item!

```
@description
Converts a value (stored on the given 'item-path') to boolean and toggles it (true > false, false > true).
```

```
@param (vector) item-path
```

```
@usage
(r toggle-item! db [:my-item])
```

```
@example
(def db {:my-item false})
(r toggle-item! db [:my-item])
=>
{:my-item true}
```

```
@example
(def db {:my-item nil})
(r toggle-item! db [:my-item])
=>
{:my-item true}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn toggle-item!
  [db [_ item-path]]
  (update-in db item-path not))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [toggle-item!]]))

(re-frame.db.api/toggle-item! ...)
(toggle-item!                 ...)
```

</details>

---

### toggle-item-value!

```
@description
- If the value stored on the given 'item-path' equals to the given 'item-value' dissociates otherwise associates it.
- E.g., if the '[:my-item]' path contains "My string" and the given 'item-value' is also "My string"
  it overwrites the '[:my-item]' path with NIL, otherwise it writes the "My string" value to the '[:my-item]' path.
```

```
@param (vector) item-path
@param (*) item-value
```

```
@usage
(r toggle-item-value! db [:my-item] :my-value)
```

```
@example
(def db {:my-item :my-value})
(r toggle-item-value! db [:my-item] :my-value)
=>
{}
```

```
@example
(def db {:my-item :anything-else})
(r toggle-item-value! db [:my-item] :my-value)
=>
{:my-item :my-value}
```

```
@return (map)
```

<details>
<summary>Source code</summary>

```
(defn toggle-item-value!
  [db [_ item-path item-value]]
  (let [stored-value (get-in db item-path)]
       (if (= stored-value item-value)
           (dissoc-in db item-path)
           (assoc-in  db item-path item-value))))
```

</details>

<details>
<summary>Require</summary>

```
(ns my-namespace (:require [re-frame.db.api :refer [toggle-item-value!]]))

(re-frame.db.api/toggle-item-value! ...)
(toggle-item-value!                 ...)
```

</details>

---

<sub>This documentation is generated with the [clj-docs-generator](https://github.com/bithandshake/clj-docs-generator) engine.</sub>

