(ns mydemocracy.core
  (:require [uix.dom.alpha :as uix.dom]
            [uix.core.alpha :as uix]
            [xframe.core.alpha :as xf :refer [<sub]]
            [ajax.core :as ajax]
            ["react" :as react]

            [mydemocracy.fx]
            [mydemocracy.subs]
            [mydemocracy.util :as util]
            [mydemocracy.firebase :as firebase]
            [mydemocracy.firebase-auth-ui :as auth-ui]))

(defn ! [& evt]
  (xf/dispatch evt))

(defn <- [k]
  (<sub [:get k]))

(defn header []
  [:header {:class "govuk-header ", :role "banner", :data-module "govuk-header"}
   [:div {:style {:border-bottom "0px solid #b9deff"}
          :class "govuk-header__container govuk-width-container"}
    [:div {:class "govuk-header__logo"}
     [:a {:href "/", :class "govuk-header__link govuk-header__link--homepage"}
      [:span {:class "govuk-header__logotype"}
       #_[:svg {:aria-hidden "true", :focusable "false", :class "govuk-header__logotype-crown", :xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 132 97", :height "30", :width "36"}
        [:path {:fill "currentColor", :fill-rule "evenodd", :d "M25 30.2c3.5 1.5 7.7-.2 9.1-3.7 1.5-3.6-.2-7.8-3.9-9.2-3.6-1.4-7.6.3-9.1 3.9-1.4 3.5.3 7.5 3.9 9zM9 39.5c3.6 1.5 7.8-.2 9.2-3.7 1.5-3.6-.2-7.8-3.9-9.1-3.6-1.5-7.6.2-9.1 3.8-1.4 3.5.3 7.5 3.8 9zM4.4 57.2c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.5-1.5-7.6.3-9.1 3.8-1.4 3.5.3 7.6 3.9 9.1zm38.3-21.4c3.5 1.5 7.7-.2 9.1-3.8 1.5-3.6-.2-7.7-3.9-9.1-3.6-1.5-7.6.3-9.1 3.8-1.3 3.6.4 7.7 3.9 9.1zm64.4-5.6c-3.6 1.5-7.8-.2-9.1-3.7-1.5-3.6.2-7.8 3.8-9.2 3.6-1.4 7.7.3 9.2 3.9 1.3 3.5-.4 7.5-3.9 9zm15.9 9.3c-3.6 1.5-7.7-.2-9.1-3.7-1.5-3.6.2-7.8 3.7-9.1 3.6-1.5 7.7.2 9.2 3.8 1.5 3.5-.3 7.5-3.8 9zm4.7 17.7c-3.6 1.5-7.8-.2-9.2-3.8-1.5-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.3 3.5-.4 7.6-3.9 9.1zM89.3 35.8c-3.6 1.5-7.8-.2-9.2-3.8-1.4-3.6.2-7.7 3.9-9.1 3.6-1.5 7.7.3 9.2 3.8 1.4 3.6-.3 7.7-3.9 9.1zM69.7 17.7l8.9 4.7V9.3l-8.9 2.8c-.2-.3-.5-.6-.9-.9L72.4 0H59.6l3.5 11.2c-.3.3-.6.5-.9.9l-8.8-2.8v13.1l8.8-4.7c.3.3.6.7.9.9l-5 15.4v.1c-.2.8-.4 1.6-.4 2.4 0 4.1 3.1 7.5 7 8.1h.2c.3 0 .7.1 1 .1.4 0 .7 0 1-.1h.2c4-.6 7.1-4.1 7.1-8.1 0-.8-.1-1.7-.4-2.4V34l-5.1-15.4c.4-.2.7-.6 1-.9zM66 92.8c16.9 0 32.8 1.1 47.1 3.2 4-16.9 8.9-26.7 14-33.5l-9.6-3.4c1 4.9 1.1 7.2 0 10.2-1.5-1.4-3-4.3-4.2-8.7L108.6 76c2.8-2 5-3.2 7.5-3.3-4.4 9.4-10 11.9-13.6 11.2-4.3-.8-6.3-4.6-5.6-7.9 1-4.7 5.7-5.9 8-.5 4.3-8.7-3-11.4-7.6-8.8 7.1-7.2 7.9-13.5 2.1-21.1-8 6.1-8.1 12.3-4.5 20.8-4.7-5.4-12.1-2.5-9.5 6.2 3.4-5.2 7.9-2 7.2 3.1-.6 4.3-6.4 7.8-13.5 7.2-10.3-.9-10.9-8-11.2-13.8 2.5-.5 7.1 1.8 11 7.3L80.2 60c-4.1 4.4-8 5.3-12.3 5.4 1.4-4.4 8-11.6 8-11.6H55.5s6.4 7.2 7.9 11.6c-4.2-.1-8-1-12.3-5.4l1.4 16.4c3.9-5.5 8.5-7.7 10.9-7.3-.3 5.8-.9 12.8-11.1 13.8-7.2.6-12.9-2.9-13.5-7.2-.7-5 3.8-8.3 7.1-3.1 2.7-8.7-4.6-11.6-9.4-6.2 3.7-8.5 3.6-14.7-4.6-20.8-5.8 7.6-5 13.9 2.2 21.1-4.7-2.6-11.9.1-7.7 8.8 2.3-5.5 7.1-4.2 8.1.5.7 3.3-1.3 7.1-5.7 7.9-3.5.7-9-1.8-13.5-11.2 2.5.1 4.7 1.3 7.5 3.3l-4.7-15.4c-1.2 4.4-2.7 7.2-4.3 8.7-1.1-3-.9-5.3 0-10.2l-9.5 3.4c5 6.9 9.9 16.7 14 33.5 14.8-2.1 30.8-3.2 47.7-3.2z"}]
        #_[:image {:src "/assets/images/govuk-logotype-crown.png", :display "none", :class "govuk-header__logotype-crown-fallback-image", :width "36", :height "32"}]]
       [:span {:class "govuk-header__logotype-text"} " OURGOV.UK"]]]]
    [:button {:style {:float "right"
                      :margin-bottom 0
                      :padding-top 3
                      :padding-bottom 4}
              :class "govuk-button"
              :data-module "govuk-button"
              :on-click #(! :firebase/sign-out)} 
     "Sign out"]]])

(defn header-right []
  [:div {:class "app-site-search", :data-module "app-search", :data-search-index "/search-index-85e7b88e7cd608f4d1b24f23a8513615.json"}
   [:label {:class "govuk-visually-hidden", :for "app-site-search__input"} "Search Design system"]
   [:a {:class "app-site-search__link govuk-link", :href "/sitemap"} "Sitemap"]
   [:div {:class "app-site-search__wrapper"}
    [:div {:style "border: 0px; clip: rect(0px, 0px, 0px, 0px); height: 1px; margin-bottom: -1px; margin-right: -1px; overflow: hidden; padding: 0px; position: absolute; white-space: nowrap; width: 1px;"}
     [:div {:id "app-site-search__input__status--A", :role "status", :aria-atomic "true", :aria-live "polite"}]
     [:div {:id "app-site-search__input__status--B", :role "status", :aria-atomic "true", :aria-live "polite"}]]
    [:input {:role "combobox", :placeholder "Search Design System", :aria-expanded "false", :name "input-autocomplete", :type "text", :id "app-site-search__input", :class "app-site-search__input app-site-search__input--default", :autocomplete "off", :aria-owns "app-site-search__input__listbox", :aria-autocomplete "both"}]
    [:ul {:class "app-site-search__menu app-site-search__menu--overlay app-site-search__menu--hidden", :id "app-site-search__input__listbox", :role "listbox"}]
    [:span {:id "app-site-search__input__assistiveHint", :style "display: none;"} "When autocomplete results are available use up and down arrows to review and enter to select.  Touch device users, explore by touch or with swipe gestures."]]])

(defn layout-2-thirds-col [{:keys [l-header l-lead l-content r-header r-content]}]
  [:div {:class "govuk-grid-row"}
   [:div {:class "govuk-grid-column-two-thirds"}
    [:h2 {:class "govuk-heading-l"} l-header]
    [:p {:class "govuk-body-l"} l-lead]
    [:p {:class "govuk-body"} l-content]]
   [:div {:class "govuk-grid-column-one-third"}
    [:h3 {:class "govuk-heading-m"} r-header]
    [:p {:class "govuk-body"} r-content]]])

(defn textarea [title description fieldname db-key]
  [:div {:class "govuk-form-group"}
   [:h1 {:class "govuk-label-wrapper"}
    [:label {:class "govuk-label govuk-label--l", :for "more-detail"} title]]
   [:div {:id "more-detail-hint", :class "govuk-hint"} description]
   [:textarea {:class "govuk-textarea"
               :rows "7"

               :id fieldname
               :name fieldname
               :value (or (<- db-key)
                          ""
                          ;(get data field-name)
                          )
               :on-change #(! :set [db-key] (-> % .-target .-value))}]])

(defn start-page []
  [:main {:class "govuk-main-wrapper ", :id "main-content", :role "main"}
   [:div {:class "govuk-grid-row"}
    [:div {:class "govuk-grid-column-two-thirds"}
     [:h1 {:class "govuk-heading-l"} "Service name goes here"]
     [:p {:class "govuk-body"} "Use this service to:"]
     [:ul {:class "govuk-list govuk-list--bullet"}
      [:li "do something"]
      [:li "update your name, address or other details"]
      [:li "do something else"]]
     [:p {:class "govuk-body"} "Registering takes around 5 minutes."]
     [:a {:href "#", :role "button", :draggable "false", :class "govuk-button govuk-!-margin-top-2 govuk-!-margin-bottom-8 govuk-button--start", :data-module "govuk-button"} "Start now"
      [:svg {:class "govuk-button__start-icon", :xmlns "http://www.w3.org/2000/svg", :width "17.5", :height "19", :viewBox "0 0 33 40", :aria-hidden "true", :focusable "false"}
       [:path {:fill "currentColor", :d "M0 0h13l20 20-20 20H0l20-20z"}]]]
     [:h2 {:class "govuk-heading-m"} "Before you start"]
     [:p {:class "govuk-body"} "You can also "
      [:a {:class "govuk-link", :href "#"} "register by post"] "."]
     [:p {:class "govuk-body"} "The online service is also available in "
      [:a {:class "govuk-link", :href "#"} "Welsh (Cymraeg)"] "."]
     [:p {:class "govuk-body"} "You cannot register for this service if you’re in the UK illegally."]]
    [:div {:class "govuk-grid-column-one-third"}
     [:aside {:class "app-related-items", :role "complementary"}
      [:h2 {:class "govuk-heading-m", :id "subsection-title"} "Subsection"]
      [:nav {:role "navigation", :aria-labelledby "subsection-title"}
       [:ul {:class "govuk-list govuk-!-font-size-16"}
        [:li
         [:a {:class "govuk-link", :href "#"} "Related link"]]
        [:li
         [:a {:class "govuk-link", :href "#"} "Related link"]]
        [:li
         [:a {:href "#", :class "govuk-link govuk-!-font-weight-bold"} "More "
          [:span {:class "govuk-visually-hidden"} "in Subsection"]]]]]]]]])

(defn products []
  (uix/with-effect []
    (firebase/get-once ["letters"] #(xf/dispatch [:set [:letters] %])))
  (util/for-idx i [letter (<sub [:get :letters])]
                [:div.letter (pr-str letter)]))

(defn logged-in []
  [:<>
   [products]
   [textarea "Submit a letter" "Enter the main content of your letter here" "letter-content" :letter-content]
   [:button {:class "govuk-button", :data-module "govuk-button" 
             :on-click #(! :submit-letter)} "Submit letter"]
   [start-page]
   [:button {:on-click #(xf/dispatch [:firebase/sign-out])}
    "log out"]])

(defn footer []
  [:footer {:class "govuk-footer ", :role "contentinfo"}
   [:div {:class "govuk-width-container "}
    [:div {:class "govuk-footer__meta"}
     [:div {:class "govuk-footer__meta-item govuk-footer__meta-item--grow"}
      [:h2 {:class "govuk-visually-hidden"} "Support links"]
      [:ul {:class "govuk-footer__inline-list"}
       [:li {:class "govuk-footer__inline-list-item"}
        [:a {:class "govuk-footer__link", :href "#1"} "Item 1"]]
       [:li {:class "govuk-footer__inline-list-item"}
        [:a {:class "govuk-footer__link", :href "#2"} "Item 2"]]
       [:li {:class "govuk-footer__inline-list-item"}
        [:a {:class "govuk-footer__link", :href "#3"} "Item 3"]]]
      #_[:svg {:aria-hidden "true", :focusable "false", :class "govuk-footer__licence-logo", :xmlns "http://www.w3.org/2000/svg", :viewBox "0 0 483.2 195.7", :height "17", :width "41"}
       [:path {:fill "currentColor", :d "M421.5 142.8V.1l-50.7 32.3v161.1h112.4v-50.7zm-122.3-9.6A47.12 47.12 0 0 1 221 97.8c0-26 21.1-47.1 47.1-47.1 16.7 0 31.4 8.7 39.7 21.8l42.7-27.2A97.63 97.63 0 0 0 268.1 0c-36.5 0-68.3 20.1-85.1 49.7A98 98 0 0 0 97.8 0C43.9 0 0 43.9 0 97.8s43.9 97.8 97.8 97.8c36.5 0 68.3-20.1 85.1-49.7a97.76 97.76 0 0 0 149.6 25.4l19.4 22.2h3v-87.8h-80l24.3 27.5zM97.8 145c-26 0-47.1-21.1-47.1-47.1s21.1-47.1 47.1-47.1 47.2 21 47.2 47S123.8 145 97.8 145"}]]
      #_[:span {:class "govuk-footer__licence-description"} "All content is available under the "
       [:a {:class "govuk-footer__link", :href "https://www.nationalarchives.gov.uk/doc/open-government-licence/version/3/", :rel "license"} "Open Government Licence v3.0"] ", except where otherwise stated"]]
     #_[:div {:class "govuk-footer__meta-item"}
      [:a {:class "govuk-footer__link govuk-footer__copyright-logo", :href ""} "© Copyright OurGov"]]]]])

(defn main []
  [:<>
   [header]
   [:main {:class "govuk-main-wrapper"}
    [:div.govuk-width-container
     [:div {:class "govuk-grid-row"}
      [:div {:class "govuk-grid-column-two-thirds"}
       [:h1 {:class "govuk-heading-l"} "OurGov"]
       [:p "Activating and amplifying our democratic voices"]
       [:p "The aim of this site is to help the public write letters to their MPs."]
       [:p "The site will contain a searchable catalogue of letters that others have written on certain topics, details of the ‘stock replies’ that MPs give out on certain issues (so that people can address these arguments directly in advance), and a composition help tool (like predictive text but specifically designed for writing letters to an MP on your issue)."]
       [:p "The OURGOV.UK site is in not an official government service, but a non-profit initiative run by members of the public to increase public engagement with their government."]]]
     
     [layout-2-thirds-col {:title "OurGov"
                           :l-header "Left Header"
                           :l-lead "Lead paragraph text goes here"
                           :l-content "some other content goes here"
                           :r-header "Right Header"
                           :r-content "some content goes here also"}]
     [:div#content
      (if (<- :firebase/user)
        [logged-in]
        [auth-ui/login-create-account])]]]
   [footer]])

(defn ^:dev/after-load start []
  (uix.dom/render [main] (.getElementById js/document "app"))
  (firebase/init))

(defn init []
  (xf/dispatch [:db/init])
  (start))