
(ns re-frame.db.events
    (:require [fruits.map.api    :refer [dissoc-in]]
              [fruits.vector.api :as vector]
              [re-frame.core     :refer [reg-event-db]]
              [re-frame.db.utils :as utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn empty-db!
  ; @description
  ; Returns the db as an empty map.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (empty-db! db [:my-handler])
  ; =>
  ; {}
  ;
  ; @usage
  ; (dispatch [:empty-db!])
  ;
  ; @return (map)
  [_ _]
  (-> {}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn set-item!
  ; @description
  ; Overwrites a specific item with the given value in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (*) item-value]
  ;
  ; @usage
  ; (def db {})
  ; (set-item! db [:my-handler [:my-item] "My value"])
  ; =>
  ; {:my-item "My value"}
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (set-item! db [:my-handler [:my-item] nil])
  ; =>
  ; {}
  ;
  ; @usage
  ; (def db {})
  ; (set-item! db [:my-handler [:my-item 0] "My value"])
  ; =>
  ; {:my-item ["My value"]}
  ;
  ; @usage
  ; (dispatch [:set-item! [:my-item] "My value"])
  ;
  ; @return (map)
  [db [_ item-path item-value]]
  (if (utils/vector-item-path? item-path)
      (let [parent-path (utils/parent-path     item-path)
            item-dex    (utils/vector-item-dex item-path)]
           (update-in db parent-path vector/upsert-nth-item item-dex item-value))
      (if (-> item-value nil?)
          (-> db (dissoc-in item-path))
          (-> db (assoc-in  item-path item-value)))))

(defn remove-item!
  ; @description
  ; Removes a specific item from the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (remove-item! db [:my-handler [:my-item]])
  ; =>
  ; {}
  ;
  ; @usage
  ; (def db {:my-item ["My value"]})
  ; (remove-item! db [:my-handler [:my-item 0]])
  ; =>
  ; {:my-item []}
  ;
  ; @usage
  ; (dispatch [:remove-item! [:my-item]])
  ;
  ; @return (map)
  [db [_ item-path]]
  (if (utils/vector-item-path? item-path)
      (let [parent-path (utils/parent-path     item-path)
            item-dex    (utils/vector-item-dex item-path)]
           (update-in db parent-path vector/remove-nth-item item-dex))
      (dissoc-in db item-path)))

(defn update-item!
  ; @description
  ; Applies the given 'f' function on a specific item in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (function) f
  ;  (list of *)(opt) params]
  ;
  ; @usage
  ; (update-item! db [:my-handler [:my-item] not])
  ;
  ; @example
  ; (def db {:my-item false})
  ; (update-item! db [:my-handler [:my-item] not])
  ; =>
  ; {:my-item true}
  ;
  ; @example
  ; (def db {:my-item [:pear]})
  ; (update-item! db [:my-handler [:my-item] conj :apple])
  ; =>
  ; {:my-item [:pear :apple]}
  ;
  ; @usage
  ; (dispatch [:update-item! [:my-item] conj :apple])
  ;
  ; @return (map)
  [db [_ item-path f & params]]
  (if (utils/vector-item-path? item-path)
      (let [parent-path (utils/parent-path     item-path)
            item-dex    (utils/vector-item-dex item-path)]
           (apply update-in db parent-path vector/update-nth-item item-dex f params))
      (apply update-in db item-path f params)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn toggle-item!
  ; @description
  ; Converts the value of a specific item in the db into boolean and toggles it (true > false, false > true).
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item false})
  ; (toggle-item! db [:my-handler [:my-item]])
  ; =>
  ; {:my-item true}
  ;
  ; @usage
  ; (def db {})
  ; (toggle-item! db [:my-handler [:my-item]])
  ; =>
  ; {:my-item true}
  ;
  ; @usage
  ; (def db {:my-item [false]})
  ; (toggle-item! db [:my-handler [:my-item 0]])
  ; =>
  ; {:my-item [true]}
  ;
  ; @usage
  ; (dispatch [:toggle-item! [:my-item]])
  ;
  ; @return (map)
  [db [handler-id item-path]]
  (update-item! db [handler-id item-path not]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn copy-item!
  ; @description
  ; Duplicates a specific item in the db to the given copy path.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (vector) copy-path]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (copy-item! db [:my-handler [:my-item] [:copy-item]])
  ; =>
  ; {:my-item   "My value"
  ;  :copy-item "My value"}
  ;
  ; @usage
  ; (dispatch [:copy-item! [:my-item] [:copy-item]])
  ;
  ; @return (map)
  [db [handler-id item-path copy-path]]
  (let [item-value (get-in db item-path)]
       (set-item! db [handler-id copy-path item-value])))

(defn move-item!
  ; @description
  ; Moves a specific item in the db to the given destination path.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (vector) destination-path]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (move-item! db [:my-handler [:my-item] [:another-item]])
  ; =>
  ; {:another-item "My value"}
  ;
  ; @usage
  ; (dispatch [:move-item! [:my-item] [:another-item]])
  ;
  ; @return (map)
  [db [handler-id item-path destination-path]]
  (let [item-value (get-in db item-path)]
       (-> db (remove-item! [handler-id item-path])
              (set-item!    [handler-id destination-path item-value]))))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn inc-item!
  ; @description
  ; Increments the value of a specific item in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item 42})
  ; (inc-item! db [:my-handler [:my-item]])
  ; =>
  ; {:my-item 43}
  ;
  ; @usage
  ; (dispatch [:inc-item! [:my-item]])
  ;
  ; @return (map)
  [db [handler-id item-path]]
  (update-item! db [handler-id item-path inc]))

(defn dec-item!
  ; @description
  ; Decrements the value of a specific item in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item 42})
  ; (inc-item! db [:my-handler [:my-item]])
  ; =>
  ; {:my-item 41}
  ;
  ; @usage
  ; (dispatch [:dec-item! [:my-item]])
  ;
  ; @return (map)
  [db [handler-id item-path]]
  (update-item! db [handler-id item-path dec]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:empty-db!]
(reg-event-db :empty-db! empty-db!)

; @usage
; [:set-item! [:my-item] "My value"]
(reg-event-db :set-item! set-item!)

; @usage
; [:remove-item! [:my-item]]
(reg-event-db :remove-item! remove-item!)

; @usage
; [:update-item! [:my-item] conj :apple]
(reg-event-db :update-item! update-item!)

; @usage
; [:toggle-item! [:my-item]]
(reg-event-db :toggle-item! toggle-item!)

; @usage
; [:move-item! [:my-item] [:copy-item]]
(reg-event-db :copy-item! copy-item!)

; @usage
; [:move-item! [:my-item] [:another-item]]
(reg-event-db :move-item! move-item!)

; @usage
; [:inc-item! [:my-item]]
(reg-event-db :inc-item! inc-item!)

; @usage
; [:dec-item! [:my-item]]
(reg-event-db :dec-item! dec-item!)
