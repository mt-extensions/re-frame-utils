
(ns re-frame.db.env
    (:require [re-frame.core :refer [subscribe]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn subscribe-item
  ; @description
  ; Returns an atom that can be deref'ed to the actual value of a specific item from the db.
  ;
  ; @param (vector) item-path
  ; @param (*)(opt) default-value
  ;
  ; @usage
  ; (deref (subscribe-item [:my-item]))
  ; =>
  ; "My value"
  ;
  ; @return (atom)
  ([item-path]
   (-> [:get-item item-path] subscribe))

  ([item-path default-value]
   (-> [:get-item item-path default-value] subscribe)))

(defn subscribed-item
  ; @description
  ; Returns the actual deref'ed value of a specific item from the db.
  ;
  ; @param (vector) item-path
  ; @param (*)(opt) default-value
  ;
  ; @usage
  ; (subscribed-item [:my-item])
  ; =>
  ; "My value"
  ;
  ; @return (*)
  ([item-path]
   (-> [:get-item item-path] subscribe deref))

  ([item-path default-value]
   (-> [:get-item item-path default-value] subscribe deref)))
