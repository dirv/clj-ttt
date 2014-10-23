(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]
            [clj-ttt.computer-player :refer :all]))

(defn- output-board-line [square-strings]
  (str (clojure.string/join " " square-strings) "\n"))

(defn- number-or-mark [index mark playable-squares]
  (if (some #{index} playable-squares)
    (str (inc index))
    (str mark)))

(defn- numbers-or-marks [board]
  (map-indexed #(number-or-mark %1 %2 (playable-squares board)) board))

(defn- padding-format-string [strs]
  (str "%" (apply max (map count strs)) "s"))

(defn- padded-numbers-or-marks [board]
  (let [all-strs (numbers-or-marks board)]
    (map #(format (padding-format-string all-strs) %) all-strs)))

(defn board-string [board]
  (apply str (map output-board-line (partition (size board) (padded-numbers-or-marks board)))))

(defn play-message [board]
  (if (won? board)
    (str (name (last-player board)) " wins!")
    (if (finished? board)
      (str "It's a draw!")
      (str (name (next-player board)) "'s go, choose a square: "))))

(defn display [board]
  (str (board-string board) (play-message board)))

(defn read-move []
  (dec (read-string (read-line))))

(defn read-human [player]
  (println (str "Is player " (name player) " human? "))
  (if (= "y" (read-line))
    :human
    :computer))

(defn read-board-size []
  (println "What size of game would you like to play? 3 or 4 is an appropriate choice.")
  (read-string (read-line)))

(defn print-board [board]
  (println (display board)))

(defmulti make-player-move (fn [board x o] (if-x-is-next board x o)))
(defmethod make-player-move :human [board x o]
  (play-move board (read-move) (next-player board)))
(defmethod make-player-move :computer [board x o]
  (choose-next-move board (next-player board)))

(defn play-until-finish [board x o]
  (print-board board)
  (if-not (finished? board)
    (recur (make-player-move board x o) x o)))

(defn prompt-and-play []
  (let [size (read-board-size)
        x (read-human :X)
        o (read-human :O)]
    (play-until-finish (create-board size) x o)))
