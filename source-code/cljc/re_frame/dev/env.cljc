
(ns re-frame.dev.env
    (:require [re-frame.registrar :as registrar]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-event-handlers
  ; @description
  ; Returns the registered event handlers optionally filtered by the given 'event-kind' value.
  ;
  ; @param (keyword)(opt) event-kind
  ; :cofx, :event, :fx, :sub
  ;
  ; @usage
  ; (get-event-handlers)
  ; =>
  ; {:cofx  {:my-cofx-handler  (fn [_ _] ...)}
  ;  :event {:my-event-handler (fn [_ _] ...)}
  ;  :fx    {:my-fx-handler    (fn [_ _] ...)}
  ;  :sub   {:my-sub-handler   (fn [_ _] ...)}}
  ;
  ; @usage
  ; (get-event-handlers :sub)
  ; =>
  ; {:my-sub-handler (fn [_ _] ...)}
  ;
  ; @return (map)
  ; {:cofx (map)
  ;  :event (map)
  ;  :fx (map)
  ;  :sub (map)}
  ([]                       (deref registrar/kind->id->handler))
  ([event-kind] (event-kind (deref registrar/kind->id->handler))))

(defn get-event-handler
  ; @description
  ; Returns a registered event handler found by the given 'event-kind' and 'event-id' values.
  ;
  ; @param (keyword) event-kind
  ; :cofx, :event, :fx, :sub
  ; @param (keyword) event-id
  ;
  ; @usage
  ; (get-event-handler :sub :my-subscription)
  ; =>
  ; (fn [_ _] ...)
  ;
  ; @return (function)
  [event-kind event-id]
  (-> (get-event-handlers)
      (get-in [event-kind event-id])))

(defn event-handler-registered?
  ; @description
  ; Returns TRUE if a registered event handler is found by the given 'event-kind' and 'event-id' values.
  ;
  ; @param (keyword) event-kind
  ; :cofx, :event, :fx, :sub
  ; @param (keyword) event-id
  ;
  ; @usage
  ; (event-handler-registered? :sub :my-subscription)
  ; =>
  ; true
  ;
  ; @return (function)
  [event-kind event-id]
  (-> (get-event-handler event-kind event-id)
      (some?)))
