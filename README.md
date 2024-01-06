﻿## 1121 Android 程式設計 期末專案
### 中原實習整合平台
### 人生缺點什麼，「職」缺你

*****


#### 一、需求議題內容說明

當學生需要在不同系所尋找實習機會時，他們必須在不同系所查找相關資訊，而每個系所都使用不同的平台發佈實習資訊，使整個尋找流程變得相當複雜。這種情況下，學生僅能獲取所在系所的實習資訊，無法得知其他系所的機會。此外，需要在各平台間比較和尋找也相當耗時。學生在尋找實習機會時受限於不同系所的公告平台，導致資訊分散且難以統一查找。學生需要更有效率的方式來查找、篩選並快速應徵實習職缺。同時，缺乏即時性的資訊推播機制可能使他們錯失重要的實習機會。


#### 二、解決方案

為了解決這個問題，我們設立了一個整合性的實習資訊平台，旨在提供清晰且方便的實習職缺資訊。透過這個平台，學校人員能夠將系上的實習機會資訊統一公告於本平台給校內學生和人員。學生可以在平台上快速查找感興趣的實習機會，並根據自己關心的條件，如實習地區等進行快速篩選和比較，還可以收藏感興趣的職缺。
我們提供詳細的職缺資訊，包括公司名稱、地點、Email、電話等，以確保學生能夠清楚地了解每個實習職缺。此外，我們設有推播功能，當有新的實習公告時，即時通知學生，避免他們錯失任何一個實習機會。如果學生有意向投遞履歷，我們也提供一鍵寄送Email給實習公司的功能。
透過本整合系統，我們期望簡化學生尋找實習機會和投遞履歷的流程，使其更加順暢和高效。統一實習機會的系統不僅能節省時間，還能提高尋找效率。我們致力於提供一個方便、直觀且全面的資訊系統，讓學生更容易地追求和獲得理想的實習機會。


#### 三、主要優勢

(1)	節省搜索時間
一站式平台提供統一的實習資訊，學生不再需要在不同系所的平台間反覆搜尋，節省尋找實習機會的時間。

(2)	資訊可及性提升
學生能夠一次性獲得所有系所的實習資訊，提高了資訊的可及性，解決了學生只能得知所在系所實習機會的限制，擴大了資訊的涵蓋面。

(3)	查找效率提升
平台提供篩選和收藏功能，使學生更快速地找到符合個人需求的實習機會。可根據個人條件，如實習地區等進行快速篩選，提高了查找效率。

(4)	資訊明確度提高
提供詳細的實習職缺資訊，如公司名稱、地點、聯絡方式等，使學生更清楚了解每個實習機會，有助於學生更有針對性地投遞履歷，提高成功率。

(5)	機會覺知性增強
即時的推播功能確保學生在有新的實習公告時能夠立即得知，避免錯失重要機會。

(6)	履歷投遞便捷性提高
學生可以在平台上編輯並保存履歷，一鍵寄送給實習公司，簡化了投遞履歷的流程，提高了學生與企業之間的互動效率。


#### 四、平台功能

目前平台分兩端：1.學校人員端 2.學生端。

兩者的差異在於學校人員可以新增實習資訊並公告，但無履歷功能。而學生則是無法公告實習資訊，但有履歷功能且可以快速應徵(可一鍵寄送履歷至實習公司的mail)。

共同功能：

i.	個人基本資料：
學校人員和學生都可以管理和更新自己的基本資料，確保資訊的準確性。

ii.	實習職缺總覽：
提供所有實習職缺的總覽，讓使用者一目了然。

iii.篩選功能：
允許使用者根據特定條件，如地區、職缺類型等進行篩選，以找到符合需求的實習機會。

iv.	收藏清單總覽：
使用者可以查看已收藏的實習職缺，方便管理有興趣的職缺與比較。

v.	收藏功能：
允許使用者收藏感興趣的實習職缺，方便日後查閱。

vi.	職缺詳細資訊：
點進去可查看每個實習機會的詳細資訊，包括公司名稱、地點、職缺描述等。

vii.	推播新實習機會：
提供即時的推播功能，通知使用者有新的實習機會，確保不錯過任何重要信息。


#### 五、平台影片

影片連結：https://youtu.be/AiPJyq8mLpU 


#### 六、平台使用技術

使用Kotlin開發Android應用程式，資料庫則使用MySQL進行串接，推播功能則是使用Firebase的 FCM (Firebase Cloud Messaging)服務。
