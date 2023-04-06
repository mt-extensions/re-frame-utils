
(ns re-frame.db.subs
    (:require [noop.api      :refer [return]]
              [re-frame.core :refer [reg-sub]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-db
  ; @return (map)
  [db _]
  (return db))

(defn get-item
  ; @param (vector) item-path
  ; @param (*)(opt) default-value
  ;
  ; @usage
  ; (r get-item [:my-item])
  ;
  ; @usage
  ; (r get-item [:my-item] "Default value")
  ;
  ; @return (*)
  [db [_ item-path default-value]]
  (get-in db item-path default-value))

(defn item-exists?
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r item-exists? [:my-item])
  ;
  ; @return (boolean)
  [db [_ item-path]]
  (some? (get-in db item-path)))

(defn get-item-count
  ; @param (vector) item-path
  ;
  ; @usage
  ; (r get-item-count [:my-item])
  ;
  ; @return (integer)
  [db [_ item-path]]
  (let [item (get-in db item-path)]
       (count item)))

(defn get-applied-item
  ; @param (vector) item-path
  ; @param (function) f
  ; @param (list of *) params
  ;
  ; @usage
  ; (r get-applied-item [:my-item] inc)
  ;
  ; @usage
  ; (r get-applied-item [:my-item] + 42)
  ;
  ; @return (integer)
  [db [_ item-path f & params]]
  (let [item   (get-in db item-path)
        params (cons item params)]
       (apply f params)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db]
(reg-sub :get-db get-db)

; @usage
; [:get-item [:my-item]]
(reg-sub :get-item get-item)

; @usage
; [:item-exists? [:my-item]]
(reg-sub :item-exists? item-exists?)

; @usage
; [:get-item-count [:my-item]]
(reg-sub :get-item-count get-item-count)

; @usage
; [:get-applied-item [:my-item] inc]
;
; @usage
; [:get-applied-item [:my-item] + 42]
(reg-sub :get-applied-item get-applied-item)
