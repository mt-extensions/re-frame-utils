
(ns re-frame.tools.api
    (:require [re-frame.tools.env :as env]
              [re-frame.tools.utils :as utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (re-frame.tools.env/*)
(def get-event-handlers        env/get-event-handlers)
(def get-event-handler         env/get-event-handler)
(def event-handler-registered? env/event-handler-registered?)

; @redirect (re-frame.tools.utils/*)
(def event-vector?                  utils/event-vector?)
(def event-vector<-params           utils/event-vector<-params)
(def event-vector<-subject-id       utils/event-vector<-subject-id)
(def event-vector->event-id         utils/event-vector->event-id)
(def event-vector->effect-map       utils/event-vector->effect-map)
(def event-vector->handler-f        utils/event-vector->handler-f)
(def side-effect-vector?            utils/side-effect-vector?)
(def side-effect-vector->effect-id  utils/side-effect-vector->effect-id)
(def delayed-effect-map->effect-map utils/delayed-effect-map->effect-map)
(def effect-map<-event-vector       utils/effect-map<-event-vector)
(def merge-effect-maps              utils/merge-effect-maps)
(def effect-map->handler-f          utils/effect-map->handler-f)
(def context<-subject-id            utils/context<-subject-id)
(def context->event-vector          utils/context->event-vector)
(def context->event-id              utils/context->event-id)
(def context->db-before-effect      utils/context->db-before-effect)
(def context->db-after-effect       utils/context->db-after-effect)
(def cofx->event-id                 utils/cofx->event-id)
(def cofx->event-vector             utils/cofx->event-vector)
(def metamorphic-handler->handler-f utils/metamorphic-handler->handler-f)
(def metamorphic-event->effect-map  utils/metamorphic-event->effect-map)
(def metamorphic-event<-params      utils/metamorphic-event<-params)
(def apply-fx-params                utils/apply-fx-params)
