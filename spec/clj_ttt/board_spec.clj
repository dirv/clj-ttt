(ns clj-ttt.board-spec
  (:require [speclj.core :refer :all]
            [clj-ttt.core :refer :all]))

(def ^:const unplayed \-)

(defn create-board [size]
  (apply str (replicate (* size size) unplayed)))

(defn play-move [board square mark]
   (let [bv (vec board)]
     (if (= unplayed (nth bv square))
       (apply str (assoc bv square mark))
       board)))

(describe "creating an empty board"
          (it "creates an empty board string"
              (should= "---------" (create-board 3))
          )
          (it "creates a 4x4 board string"
              (should= "----------------" (create-board 4))
          )
)

(describe "playing a move"
          (it "plays an X"
              (should= "--X------" (play-move "---------" 2 "X"))
          )
          (it "only plays a move if square is empty"
              (should= "X" (play-move "X" 0 "O")))
          )

