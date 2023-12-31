
(ns re-frame.db.events
    (:require [fruits.map.api    :refer [dissoc-in]]
              [fruits.vector.api :as vector]
              [re-frame.core     :refer [reg-event-db]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty-db!
  ; @description
  ; Returns an empty map.
  ;
  ; @usage
  ; (r empty-db! db)
  ;
  ; @example
  ; (def db {:my-item :my-value})
  ; (r empty-db! db)
  ; =>
  ; {}
  ;
  ; @return (map)
  [_ _]
  (-> {}))

(defn toggle-item!
  ; @description
  ; Converts a value (stored at the given 'item-path') into a boolean and toggles it (true > false, false > true).
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r toggle-item! db [:my-item])
  ;
  ; @example
  ; (def db {:my-item false})
  ; (r toggle-item! db [:my-item])
  ; =>
  ; {:my-item true}
  ;
  ; @example
  ; (def db {:my-item nil})
  ; (r toggle-item! db [:my-item])
  ; =>
  ; {:my-item true}
  ;
  ; @return (map)
  [db [_ item-path]]
  (update-in db item-path not))

(defn toggle-item-value!
  ; @description
  ; - If the value stored at the given 'item-path' equals to the given 'item-value', dissociates it; otherwise, associates it.
  ; - E.g., if the '[:my-item]' path contains "My string" and the given 'item-value' is also "My string"
  ;   it overwrites the '[:my-item]' path with NIL; otherwise, it writes the "My string" value to the '[:my-item]' path.
  ;
  ; @param (vector) item-path
  ; @param (*) item-value
  ;
  ; @usage
  ; (r toggle-item-value! db [:my-item] :my-value)
  ;
  ; @example
  ; (def db {:my-item :my-value})
  ; (r toggle-item-value! db [:my-item] :my-value)
  ; =>
  ; {}
  ;
  ; @example
  ; (def db {:my-item :anything-else})
  ; (r toggle-item-value! db [:my-item] :my-value)
  ; =>
  ; {:my-item :my-value}
  ;
  ; @return (map)
  [db [_ item-path item-value]]
  (let [stored-value (get-in db item-path)]
       (if (= stored-value item-value)
           (dissoc-in db item-path)
           (assoc-in  db item-path item-value))))

(defn copy-item!
  ; @description
  ; Duplicates the item at the given 'item-path' to the 'copy-path'.
  ;
  ; @param (vector) item-path
  ; @param (vector) copy-path
  ;
  ; @usage
  ; (r copy-item! db [:copy-from] [:copy-to])
  ;
  ; @example
  ; (def db {:copy-from :my-value})
  ; (r copy-item! db [:copy-from] [:copy-to])
  ; =>
  ; {:copy-from :my-value
  ;  :copy-to   :my-value}
  ;
  ; @return (map)
  [db [_ item-path copy-path]]
  (if-let [item (get-in db item-path)]
          (assoc-in  db copy-path item)
          (dissoc-in db copy-path)))

(defn move-item!
  ; @description
  ; Moves the item at the given 'item-path' to the 'destination-path'.
  ;
  ; @param (vector) item-path
  ; @param (vector) destination-path
  ;
  ; @usage
  ; (r move-item! db [:move-from] [:move-to])
  ;
  ; @example
  ; (def db {:move-from :my-value})
  ; (r move-item! db [:move-from] [:move-to])
  ; =>
  ; {:move-to :my-value}
  ;
  ; @return (map)
  [db [_ item-path destination-path]]
  (if-let [item (get-in db item-path)]
          (-> db (assoc-in  destination-path item)
                 (dissoc-in item-path))
          (dissoc-in db destination-path)))

(defn set-item!
  ; @description
  ; Writes the given 'item' to the given 'item-path'.
  ;
  ; @param (vector) item-path
  ; @param (*) item
  ;
  ; @usage
  ; (r set-item! db [:my-item] :my-value)
  ;
  ; @example
  ; (def db {})
  ; (r set-item! db [:my-item] :my-value)
  ; =>
  ; {:my-item :my-value}
  ;
  ; @return (map)
  [db [_ item-path item]]
  (if item (assoc-in  db item-path item)
           (dissoc-in db item-path)))

(defn set-vector-item!
  ; @important
  ; The last item in the given 'item-path' vector must be an integer!
  ;
  ; @description
  ; - Writes the given 'item' to the given 'item-path' (that can be a vector) to a specific index.
  ; - If the parent item ('item-path') is not a vector this function converts it into a vector that contains the given 'item'.
  ;
  ; @param (vector) item-path
  ; @param (*) item
  ;
  ; @usage
  ; (r set-vector-item! db [:my-item 0] :item-value)
  ;
  ; @example
  ; (def db {})
  ; (r set-vector-item! db [:my-item 0] :item-value)
  ; =>
  ; {:my-item [:item-value]}
  ;
  ; @example
  ; (def db {})
  ; (r set-vector-item! db [:my-item 2] :item-value)
  ; =>
  ; {:my-item [:item-value]}
  ;
  ; @example
  ; (def db {:my-item {}})
  ; (r set-vector-item! db [:my-item 0] :item-value)
  ; =>
  ; {:my-item [:item-value]}
  ;
  ; @example
  ; (def db {:my-item [])
  ; (r set-vector-item! db [:my-item 0] :item-value)
  ; =>
  ; {:my-item [:item-value]}
  ;
  ; @example
  ; (def db {:my-item [:first-value :second-value])
  ; (r set-vector-item! db [:my-item 0] :item-value)
  ; =>
  ; {:my-item [:item-value :second-value]}
  ;
  ; @return (map)
  [db [_ item-path item]]
  (let [item-parent-path (vector/remove-last-item item-path)
        item-dex         (vector/last-item        item-path)
        item-parent      (get-in db item-parent-path)]
       (if (vector/nonempty? item-parent)
           (let [updated-item-parent (vector/replace-nth-item item-parent item-dex item)]
                (assoc-in db item-parent-path updated-item-parent))
           (let [updated-item-parent [item]]
                (assoc-in db item-parent-path updated-item-parent)))))

(defn remove-item!
  ; @description
  ; Removes the item from the given 'item-path'.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r remove-item! db [:my-item])
  ;
  ; @example
  ; (def db {:my-item :my-value})
  ; (r remove-item! db [:my-item])
  ; =>
  ; {}
  ;
  ; @return (map)
  [db [_ item-path]]
  (dissoc-in db item-path))

(defn remove-item-n!
  ; @description
  ; Removes the items from the given 'item-paths'.
  ;
  ; @param (vectors in vector) item-paths
  ;
  ; @usage
  ; (r remove-item-n! db [[:my-item] [:another-item]])
  ;
  ; @example
  ; (def db {:my-item :my-value :another-item :another-value})
  ; (r remove-item-n! db [[:my-item] [:another-item]])
  ; =>
  ; {}
  ;
  ; @return (map)
  [db [_ & item-paths]]
  (letfn [(f0 [db item-path] (dissoc-in db item-path))]
         (reduce f0 db item-paths)))

(defn remove-vector-item!
  ; @important
  ; The last item in the given 'item-path' vector must be an integer!
  ;
  ; @description
  ; Removes the item from the given 'item-path' that must be a vector and the item path must contain the item's index.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r remove-vector-item! db [:my-item 0])
  ;
  ; @example
  ; (def db {:my-item [:a :b :c]})
  ; (r remove-vector-item! db [:my-item 0])
  ; =>
  ; {:my-item [:b :c]}
  ;
  ; @return (map)
  [db [_ item-path]]
  (let [parent-path         (vector/remove-last-item item-path)
        item-dex            (vector/last-item        item-path)
        parent-item         (get-in db parent-path)
        updated-parent-item (vector/remove-nth-item parent-item item-dex)]
       (assoc-in db parent-path updated-parent-item)))

(defn inc-item!
  ; @description
  ; Increases the value at the given 'item-path' by one.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r inc-item! db [:my-item])
  ;
  ; @example
  ; (def db {:my-item 42})
  ; (r inc-item! db [:my-item])
  ; =>
  ; {:my-item 43}
  ;
  ; @return (map)
  [db [_ item-path]]
  (update-in db item-path inc))

(defn inc-item-n!
  ; @description
  ; Increases the values at the given 'item-paths' by one.
  ;
  ; @param (vectors in vector) item-paths
  ;
  ; @usage
  ; (r inc-item-n! db [[:my-item] [:another-item]])
  ;
  ; @example
  ; (def db {:my-item 42 :another-item 69})
  ; (r inc-item-n! db [[:my-item] [:another-item]])
  ; =>
  ; {:my-item 43 :another-item 70}
  ;
  ; @return (map)
  [db [_ & item-paths]]
  (letfn [(f0 [db item-path] (update-in db item-path inc))]
         (reduce f0 db item-paths)))

(defn dec-item!
  ; @description
  ; Decreases the value at the given 'item-path' by one.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ;
  ; @example
  ; (def db {:my-item 42})
  ; (r inc-item! db [:my-item])
  ; =>
  ; {:my-item 41}
  ;
  ; @return (map)
  [db [_ item-path]]
  (update-in db item-path dec))

(defn dec-item-n!
  ; @description
  ; Decreases the values at the given 'item-paths' by one.
  ;
  ; @param (vectors in vector) item-paths
  ;
  ; @usage
  ; (r dec-item-n! db [[:my-item] [:another-item]])
  ;
  ; @example
  ; (def db {:my-item 42 :another-item 69})
  ; (r dec-item-n! db [[:my-item] [:another-item]])
  ; =>
  ; {:my-item 41 :another-item 68}
  ;
  ; @return (map)
  [db [_ & item-paths]]
  (letfn [(f0 [db item-path] (update-in db item-path dec))]
         (reduce f0 db item-paths)))

(defn apply-item!
  ; @description
  ; Applies the given 'f' function on the given 'item-path'.
  ;
  ; @param (vector) item-path
  ; @param (function) f
  ; @param (list of *) params
  ;
  ; @usage
  ; (r apply-item! db [:my-item] not)
  ;
  ; @example
  ; (def db {:my-item false})
  ; (r apply-item! db [:my-item] not)
  ; =>
  ; {:my-item true}
  ;
  ; @example
  ; (def db {:my-item [:pear]})
  ; (r apply-item! db [:my-item] conj :apple)
  ; =>
  ; {:my-item [:pear :apple]}
  ;
  ; @return (map)
  [db [_ item-path f & params]]
  (let [item   (get-in db item-path)
        params (cons item params)]
       (assoc-in db item-path (apply f params))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:empty-db!]
(reg-event-db :empty-db! empty-db!)

; @usage
; [:toggle-item! [:my-item]]
(reg-event-db :toggle-item! toggle-item!)

; @usage
; [:toggle-item-value! [:my-item] :my-value]
(reg-event-db :toggle-item-value! toggle-item-value!)

; @usage
; [:move-item! [:copy-from] [:copy-to]]
(reg-event-db :copy-item! copy-item!)

; @usage
; [:move-item! [:move-from] [:move-to]]
(reg-event-db :move-item! move-item!)

; @usage
; [:set-item! [:my-item] "My value"]
(reg-event-db :set-item! set-item!)

; @usage
; [:set-vector-item! [:my-item :0] "My value"]
(reg-event-db :set-vector-item! set-vector-item!)

; @usage
; [:remove-item! [:my-item]]
(reg-event-db :remove-item! remove-item!)

; @usage
; [:remove-item-n! [[:my-item ] [:another-item]]]
(reg-event-db :remove-item-n! remove-item-n!)

; @usage
; [:remove-vector-item! [:my-item 0]]
(reg-event-db :remove-vector-item! remove-vector-item!)

; @usage
; [:inc-item! [:my-item]
(reg-event-db :inc-item! inc-item!)

; @usage
; [:inc-item-n! [[:my-item] [:another-item]]]
(reg-event-db :inc-item-n! inc-item-n!)

; @usage
; [:dec-item! [:my-item]
(reg-event-db :dec-item! dec-item!)

; @usage
; [:dec-item-n! [[:my-item] [:another-item]]]
(reg-event-db :dec-item-n! dec-item-n!)

; @usage
; [:apply-item! [:my-item] merge {}]
(reg-event-db :apply-item! apply-item!)
