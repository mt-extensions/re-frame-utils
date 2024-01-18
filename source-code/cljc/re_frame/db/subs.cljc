
(ns re-frame.db.subs
    (:require [re-frame.core :refer [reg-sub]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-db
  ; @description
  ; Returns the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (get-db db [:my-handler])
  ; =>
  ; {:my-item "My value"}
  ;
  ; @usage
  ; (subscribe [:get-db])
  ;
  ; @return (map)
  [db _]
  (-> db))

(defn get-item
  ; @description
  ; Returns a specific item from the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (*)(opt) default-value]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (get-item db [:my-handler [:my-item]])
  ; =>
  ; "My value"
  ;
  ; @usage
  ; (def db {})
  ; (get-item db [:my-handler [:my-item] "My default value"])
  ; =>
  ; "My default value"
  ;
  ; @usage
  ; (subscribe [:get-item [:my-item]])
  ;
  ; @return (*)
  [db [_ item-path default-value]]
  (get-in db item-path default-value))

(defn item-exists?
  ; @description
  ; Returns TRUE if a specific item exists in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (item-exists? db [:my-handler [:my-item]])
  ; =>
  ; true
  ;
  ; @usage
  ; (subscribe [:item-exists? [:my-item]])
  ;
  ; @return (boolean)
  [db [_ item-path]]
  (let [item-value (get-in db item-path)]
       (some? item-value)))

(defn get-item-count
  ; @description
  ; Returns the number of items of a specific item in the db.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path]
  ;
  ; @usage
  ; (def db {:my-item [:a :b :c]})
  ; (get-item-count db [:my-handler [:my-item]])
  ; =>
  ; 3
  ;
  ; @usage
  ; (def db {:my-item "My value"})
  ; (get-item-count db [:my-handler [:my-item]])
  ; =>
  ; 8
  ;
  ; @usage
  ; (subscribe [:get-item-count [:my-item]])
  ;
  ; @return (integer)
  [db [_ item-path]]
  (let [item-value (get-in db item-path)]
       (if (-> item-value seqable?)
           (-> item-value count))))

(defn get-updated-item
  ; @description
  ; Returns a specific item from the db updated with the given 'f' function.
  ;
  ; @param (map) db
  ; @param (vector) handler-vector
  ; [(keyword) handler-id
  ;  (vector) item-path
  ;  (function) f
  ;  (list of *)(opt) params]
  ;
  ; @usage
  ; (def db {:my-item 42})
  ; (get-updated-item db [:my-handler [:my-item] inc])
  ; =>
  ; 43
  ;
  ; @usage
  ; (subscribe [:get-updated-item [:my-item] inc])
  ;
  ; @return (integer)
  [db [_ item-path f & params]]
  (let [item-value (get-in db item-path)]
       (apply f item-value params)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db]
(reg-sub :get-db get-db)

; @param (vector) item-path
;
; @usage
; [:get-item [:my-item]]
(reg-sub :get-item get-item)

; @param (vector) item-path
;
; @usage
; [:item-exists? [:my-item]]
(reg-sub :item-exists? item-exists?)

; @param (vector) item-path
;
; @usage
; [:get-item-count [:my-item]]
(reg-sub :get-item-count get-item-count)

; @param (vector) item-path
; @param (function) f
; @param (list of *)(opt) params
;
; @usage
; [:get-updated-item [:my-item] inc]
(reg-sub :get-updated-item get-updated-item)
