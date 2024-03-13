
(ns re-frame.db.utils
    (:require [fruits.vector.api :as vector]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn vector-item-path?
  ; @description
  ; Returns TRUE if the given item path ends with an integer, indicating that it points to a vector item in the db.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (vector-item-path? [:my-item 0])
  ; =>
  ; true
  ;
  ; @usage
  ; (vector-item-path? [:my-item])
  ; =>
  ; false
  ;
  ; @return (boolean)
  [item-path]
  (-> item-path last integer?))

(defn vector-item-dex
  ; @description
  ; Takes an item path vector and returns the item index if the last item of the given item path is an integer.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (vector-item-dex [:my-item 0])
  ; =>
  ; 0
  ;
  ; @usage
  ; (vector-item-dex [:my-item])
  ; =>
  ; nil
  ;
  ; @return (integer)
  [item-path]
  (if (-> item-path last integer?)
      (-> item-path last)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn parent-path
  ; @description
  ; Takes an item path vector and returns its parent item path.
  ;
  ; @param (vector) item-path
  ;
  ; @usage
  ; (parent-path [:my-item :my-subitem])
  ; =>
  ; [:my-item]
  ;
  ; @usage
  ; (parent-path [:my-item])
  ; =>
  ; []
  ;
  ; @usage
  ; (parent-path [])
  ; =>
  ; []
  ;
  ; @return (vector)
  [item-path]
  (vector/remove-last-item item-path))
