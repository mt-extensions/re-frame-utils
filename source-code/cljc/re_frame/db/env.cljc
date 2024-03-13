
(ns re-frame.db.env
    (:require [re-frame.core :refer [subscribe]]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn subscribe-item
  ; @description
  ; Returns a Reagent Reaction containing the value of a specific db item.
  ;
  ; @param (vector) item-path
  ; @param (*)(opt) default-value
  ;
  ; @usage
  ; (subscribe-item [:my-item])
  ; =>
  ; #object[Reagent.ratom.Reaction]
  ;
  ; @return (Reagent Reaction object)
  ([item-path]
   (-> [:get-item item-path] subscribe))

  ([item-path default-value]
   (-> [:get-item item-path default-value] subscribe)))

(defn subscribed-item
  ; @description
  ; Returns the value of a specific db item.
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
