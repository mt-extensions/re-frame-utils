
(ns re-frame.db.api
    (:require [re-frame.db.env    :as env]
              [re-frame.db.events :as events]
              [re-frame.db.subs   :as subs]
              [re-frame.db.utils :as utils]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (re-frame.db.env/*)
(def subscribe-item  env/subscribe-item)
(def subscribed-item env/subscribed-item)

; @redirect (re-frame.db.events/*)
(def empty-db!    events/empty-db!)
(def set-item!    events/set-item!)
(def remove-item! events/remove-item!)
(def update-item! events/update-item!)
(def toggle-item! events/toggle-item!)
(def copy-item!   events/copy-item!)
(def move-item!   events/move-item!)
(def inc-item!    events/inc-item!)
(def dec-item!    events/dec-item!)

; @redirect (re-frame.db.subs/*)
(def get-db           subs/get-db)
(def get-item         subs/get-item)
(def item-exists?     subs/item-exists?)
(def get-item-count   subs/get-item-count)
(def get-updated-item subs/get-updated-item)

; @redirect (re-frame.db.utils/*)
(def vector-item-path? utils/vector-item-path?)
(def vector-item-dex   utils/vector-item-dex)
(def parent-path       utils/parent-path)
