PGDMP  %    3        
        |            Youtube-Development    16.3    16.3 ;    M           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            N           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            O           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            P           1262    16398    Youtube-Development    DATABASE     �   CREATE DATABASE "Youtube-Development" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.utf8';
 %   DROP DATABASE "Youtube-Development";
                postgres    false                        2615    17568    ContentManagement    SCHEMA     #   CREATE SCHEMA "ContentManagement";
 !   DROP SCHEMA "ContentManagement";
                postgres    false                        2615    17501    UserManagement    SCHEMA         CREATE SCHEMA "UserManagement";
    DROP SCHEMA "UserManagement";
                postgres    false            �            1259    17569    Category    TABLE     k   CREATE TABLE "ContentManagement"."Category" (
    "Id" uuid NOT NULL,
    "Title" character varying(20)
);
 +   DROP TABLE "ContentManagement"."Category";
       ContentManagement         heap    postgres    false    5            �            1259    17587    Comment    TABLE     �   CREATE TABLE "ContentManagement"."Comment" (
    "Id" uuid NOT NULL,
    "Message" text,
    "SenderId" uuid NOT NULL,
    "VideoId" uuid NOT NULL,
    "ParentCommentId" uuid,
    "CommentDate" timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 *   DROP TABLE "ContentManagement"."Comment";
       ContentManagement         heap    postgres    false    5            �            1259    17641    Playlist    TABLE       CREATE TABLE "ContentManagement"."Playlist" (
    "Id" uuid NOT NULL,
    "Title" character varying(32),
    "Description" text,
    "CreatorId" uuid NOT NULL,
    "DateCreated" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "IsPublic" boolean,
    "ThumbnailPath" text
);
 +   DROP TABLE "ContentManagement"."Playlist";
       ContentManagement         heap    postgres    false    5            �            1259    17654    PlaylistDetail    TABLE     �   CREATE TABLE "ContentManagement"."PlaylistDetail" (
    "PlaylistId" uuid NOT NULL,
    "VideoId" uuid NOT NULL,
    "DateAdded" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "SequenceNumber" integer
);
 1   DROP TABLE "ContentManagement"."PlaylistDetail";
       ContentManagement         heap    postgres    false    5            �            1259    17610    UserComment    TABLE     �   CREATE TABLE "ContentManagement"."UserComment" (
    "UserId" uuid NOT NULL,
    "CommentId" uuid NOT NULL,
    "Like" boolean,
    "DateViewed" timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 .   DROP TABLE "ContentManagement"."UserComment";
       ContentManagement         heap    postgres    false    5            �            1259    17626 	   UserVideo    TABLE     ~   CREATE TABLE "ContentManagement"."UserVideo" (
    "UserId" uuid NOT NULL,
    "VideoId" uuid NOT NULL,
    "Like" boolean
);
 ,   DROP TABLE "ContentManagement"."UserVideo";
       ContentManagement         heap    postgres    false    5            �            1259    17574    Video    TABLE       CREATE TABLE "ContentManagement"."Video" (
    "Id" uuid NOT NULL,
    "Title" character varying(200),
    "Description" text,
    "ChannelId" uuid NOT NULL,
    "UploadDate" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "Path" text,
    "ThumbnailPath" text
);
 (   DROP TABLE "ContentManagement"."Video";
       ContentManagement         heap    postgres    false    5            �            1259    17670    VideoCategory    TABLE     r   CREATE TABLE "ContentManagement"."VideoCategory" (
    "VideoId" uuid NOT NULL,
    "CategoryId" uuid NOT NULL
);
 0   DROP TABLE "ContentManagement"."VideoCategory";
       ContentManagement         heap    postgres    false    5            �            1259    17526    Channel    TABLE     �   CREATE TABLE "UserManagement"."Channel" (
    "Id" uuid NOT NULL,
    "Title" character varying(20),
    "Description" text,
    "CreatorId" uuid NOT NULL,
    "DateCreated" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "ProfilePath" text
);
 '   DROP TABLE "UserManagement"."Channel";
       UserManagement         heap    postgres    false    6            �            1259    17555    Notification    TABLE     �   CREATE TABLE "UserManagement"."Notification" (
    "Id" uuid NOT NULL,
    "UserId" uuid NOT NULL,
    "Message" text,
    "IsRead" boolean DEFAULT false,
    "DateSent" timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 ,   DROP TABLE "UserManagement"."Notification";
       UserManagement         heap    postgres    false    6            �            1259    17539    Subscription    TABLE     �   CREATE TABLE "UserManagement"."Subscription" (
    "SubscriberId" uuid NOT NULL,
    "ChannelId" uuid NOT NULL,
    "JoinDate" timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
 ,   DROP TABLE "UserManagement"."Subscription";
       UserManagement         heap    postgres    false    6            �            1259    17502    User    TABLE     u  CREATE TABLE "UserManagement"."User" (
    "Id" uuid NOT NULL,
    "FullName" character varying(30),
    "Username" character varying(20),
    "Password" text,
    "Email" character varying(50),
    "DateOfBirth" character varying(50),
    "JoinDate" timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    "AvatarPath" text DEFAULT '/Images/DefaultAvatar.jpg'::text
);
 $   DROP TABLE "UserManagement"."User";
       UserManagement         heap    postgres    false    6            C          0    17569    Category 
   TABLE DATA           @   COPY "ContentManagement"."Category" ("Id", "Title") FROM stdin;
    ContentManagement          postgres    false    220   >T       E          0    17587    Comment 
   TABLE DATA           z   COPY "ContentManagement"."Comment" ("Id", "Message", "SenderId", "VideoId", "ParentCommentId", "CommentDate") FROM stdin;
    ContentManagement          postgres    false    222   |U       H          0    17641    Playlist 
   TABLE DATA           �   COPY "ContentManagement"."Playlist" ("Id", "Title", "Description", "CreatorId", "DateCreated", "IsPublic", "ThumbnailPath") FROM stdin;
    ContentManagement          postgres    false    225   ^       I          0    17654    PlaylistDetail 
   TABLE DATA           o   COPY "ContentManagement"."PlaylistDetail" ("PlaylistId", "VideoId", "DateAdded", "SequenceNumber") FROM stdin;
    ContentManagement          postgres    false    226   �a       F          0    17610    UserComment 
   TABLE DATA           a   COPY "ContentManagement"."UserComment" ("UserId", "CommentId", "Like", "DateViewed") FROM stdin;
    ContentManagement          postgres    false    223   �b       G          0    17626 	   UserVideo 
   TABLE DATA           O   COPY "ContentManagement"."UserVideo" ("UserId", "VideoId", "Like") FROM stdin;
    ContentManagement          postgres    false    224   �g       D          0    17574    Video 
   TABLE DATA           �   COPY "ContentManagement"."Video" ("Id", "Title", "Description", "ChannelId", "UploadDate", "Path", "ThumbnailPath") FROM stdin;
    ContentManagement          postgres    false    221   k       J          0    17670    VideoCategory 
   TABLE DATA           O   COPY "ContentManagement"."VideoCategory" ("VideoId", "CategoryId") FROM stdin;
    ContentManagement          postgres    false    227   �|       @          0    17526    Channel 
   TABLE DATA           v   COPY "UserManagement"."Channel" ("Id", "Title", "Description", "CreatorId", "DateCreated", "ProfilePath") FROM stdin;
    UserManagement          postgres    false    217   ~       B          0    17555    Notification 
   TABLE DATA           c   COPY "UserManagement"."Notification" ("Id", "UserId", "Message", "IsRead", "DateSent") FROM stdin;
    UserManagement          postgres    false    219   ��       A          0    17539    Subscription 
   TABLE DATA           [   COPY "UserManagement"."Subscription" ("SubscriberId", "ChannelId", "JoinDate") FROM stdin;
    UserManagement          postgres    false    218   M�       ?          0    17502    User 
   TABLE DATA           �   COPY "UserManagement"."User" ("Id", "FullName", "Username", "Password", "Email", "DateOfBirth", "JoinDate", "AvatarPath") FROM stdin;
    UserManagement          postgres    false    216   i�       �           2606    17573    Category Category_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY "ContentManagement"."Category"
    ADD CONSTRAINT "Category_pkey" PRIMARY KEY ("Id");
 Q   ALTER TABLE ONLY "ContentManagement"."Category" DROP CONSTRAINT "Category_pkey";
       ContentManagement            postgres    false    220            �           2606    17594    Comment Comment_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY "ContentManagement"."Comment"
    ADD CONSTRAINT "Comment_pkey" PRIMARY KEY ("Id");
 O   ALTER TABLE ONLY "ContentManagement"."Comment" DROP CONSTRAINT "Comment_pkey";
       ContentManagement            postgres    false    222            �           2606    17659 "   PlaylistDetail PlaylistDetail_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail"
    ADD CONSTRAINT "PlaylistDetail_pkey" PRIMARY KEY ("VideoId", "PlaylistId");
 ]   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail" DROP CONSTRAINT "PlaylistDetail_pkey";
       ContentManagement            postgres    false    226    226            �           2606    17648    Playlist Playlist_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY "ContentManagement"."Playlist"
    ADD CONSTRAINT "Playlist_pkey" PRIMARY KEY ("Id");
 Q   ALTER TABLE ONLY "ContentManagement"."Playlist" DROP CONSTRAINT "Playlist_pkey";
       ContentManagement            postgres    false    225            �           2606    17615    UserComment UserComment_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY "ContentManagement"."UserComment"
    ADD CONSTRAINT "UserComment_pkey" PRIMARY KEY ("UserId", "CommentId");
 W   ALTER TABLE ONLY "ContentManagement"."UserComment" DROP CONSTRAINT "UserComment_pkey";
       ContentManagement            postgres    false    223    223            �           2606    17630    UserVideo UserVideo_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY "ContentManagement"."UserVideo"
    ADD CONSTRAINT "UserVideo_pkey" PRIMARY KEY ("UserId", "VideoId");
 S   ALTER TABLE ONLY "ContentManagement"."UserVideo" DROP CONSTRAINT "UserVideo_pkey";
       ContentManagement            postgres    false    224    224            �           2606    17581    Video Video_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY "ContentManagement"."Video"
    ADD CONSTRAINT "Video_pkey" PRIMARY KEY ("Id");
 K   ALTER TABLE ONLY "ContentManagement"."Video" DROP CONSTRAINT "Video_pkey";
       ContentManagement            postgres    false    221            �           2606    17674     VideoCategory VidoeCategory_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."VideoCategory"
    ADD CONSTRAINT "VidoeCategory_pkey" PRIMARY KEY ("VideoId", "CategoryId");
 [   ALTER TABLE ONLY "ContentManagement"."VideoCategory" DROP CONSTRAINT "VidoeCategory_pkey";
       ContentManagement            postgres    false    227    227            �           2606    17533    Channel Channel_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY "UserManagement"."Channel"
    ADD CONSTRAINT "Channel_pkey" PRIMARY KEY ("Id");
 L   ALTER TABLE ONLY "UserManagement"."Channel" DROP CONSTRAINT "Channel_pkey";
       UserManagement            postgres    false    217            �           2606    17562    Notification Notification_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY "UserManagement"."Notification"
    ADD CONSTRAINT "Notification_pkey" PRIMARY KEY ("Id");
 V   ALTER TABLE ONLY "UserManagement"."Notification" DROP CONSTRAINT "Notification_pkey";
       UserManagement            postgres    false    219            �           2606    17544    Subscription Subscription_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY "UserManagement"."Subscription"
    ADD CONSTRAINT "Subscription_pkey" PRIMARY KEY ("SubscriberId", "ChannelId");
 V   ALTER TABLE ONLY "UserManagement"."Subscription" DROP CONSTRAINT "Subscription_pkey";
       UserManagement            postgres    false    218    218            �           2606    17509    User User_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY "UserManagement"."User"
    ADD CONSTRAINT "User_pkey" PRIMARY KEY ("Id");
 F   ALTER TABLE ONLY "UserManagement"."User" DROP CONSTRAINT "User_pkey";
       UserManagement            postgres    false    216            �           2606    17605 $   Comment Comment_ParentCommentId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."Comment"
    ADD CONSTRAINT "Comment_ParentCommentId_fkey" FOREIGN KEY ("ParentCommentId") REFERENCES "ContentManagement"."Comment"("Id");
 _   ALTER TABLE ONLY "ContentManagement"."Comment" DROP CONSTRAINT "Comment_ParentCommentId_fkey";
       ContentManagement          postgres    false    222    222    4756            �           2606    17595    Comment Comment_SenderId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."Comment"
    ADD CONSTRAINT "Comment_SenderId_fkey" FOREIGN KEY ("SenderId") REFERENCES "UserManagement"."User"("Id");
 X   ALTER TABLE ONLY "ContentManagement"."Comment" DROP CONSTRAINT "Comment_SenderId_fkey";
       ContentManagement          postgres    false    216    222    4744            �           2606    17600    Comment Comment_VideoId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."Comment"
    ADD CONSTRAINT "Comment_VideoId_fkey" FOREIGN KEY ("VideoId") REFERENCES "ContentManagement"."Video"("Id");
 W   ALTER TABLE ONLY "ContentManagement"."Comment" DROP CONSTRAINT "Comment_VideoId_fkey";
       ContentManagement          postgres    false    221    4754    222            �           2606    17665 -   PlaylistDetail PlaylistDetail_PlaylistId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail"
    ADD CONSTRAINT "PlaylistDetail_PlaylistId_fkey" FOREIGN KEY ("PlaylistId") REFERENCES "ContentManagement"."Playlist"("Id");
 h   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail" DROP CONSTRAINT "PlaylistDetail_PlaylistId_fkey";
       ContentManagement          postgres    false    225    4762    226            �           2606    17660 *   PlaylistDetail PlaylistDetail_VideoId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail"
    ADD CONSTRAINT "PlaylistDetail_VideoId_fkey" FOREIGN KEY ("VideoId") REFERENCES "ContentManagement"."Video"("Id");
 e   ALTER TABLE ONLY "ContentManagement"."PlaylistDetail" DROP CONSTRAINT "PlaylistDetail_VideoId_fkey";
       ContentManagement          postgres    false    221    226    4754            �           2606    17649     Playlist Playlist_CreatorId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."Playlist"
    ADD CONSTRAINT "Playlist_CreatorId_fkey" FOREIGN KEY ("CreatorId") REFERENCES "UserManagement"."User"("Id");
 [   ALTER TABLE ONLY "ContentManagement"."Playlist" DROP CONSTRAINT "Playlist_CreatorId_fkey";
       ContentManagement          postgres    false    216    225    4744            �           2606    17621 &   UserComment UserComment_CommentId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."UserComment"
    ADD CONSTRAINT "UserComment_CommentId_fkey" FOREIGN KEY ("CommentId") REFERENCES "ContentManagement"."Comment"("Id");
 a   ALTER TABLE ONLY "ContentManagement"."UserComment" DROP CONSTRAINT "UserComment_CommentId_fkey";
       ContentManagement          postgres    false    4756    223    222            �           2606    17616 #   UserComment UserComment_UserId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."UserComment"
    ADD CONSTRAINT "UserComment_UserId_fkey" FOREIGN KEY ("UserId") REFERENCES "UserManagement"."User"("Id");
 ^   ALTER TABLE ONLY "ContentManagement"."UserComment" DROP CONSTRAINT "UserComment_UserId_fkey";
       ContentManagement          postgres    false    4744    223    216            �           2606    17631    UserVideo UserVideo_UserId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."UserVideo"
    ADD CONSTRAINT "UserVideo_UserId_fkey" FOREIGN KEY ("UserId") REFERENCES "UserManagement"."User"("Id");
 Z   ALTER TABLE ONLY "ContentManagement"."UserVideo" DROP CONSTRAINT "UserVideo_UserId_fkey";
       ContentManagement          postgres    false    4744    216    224            �           2606    17636     UserVideo UserVideo_VideoId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."UserVideo"
    ADD CONSTRAINT "UserVideo_VideoId_fkey" FOREIGN KEY ("VideoId") REFERENCES "ContentManagement"."Video"("Id");
 [   ALTER TABLE ONLY "ContentManagement"."UserVideo" DROP CONSTRAINT "UserVideo_VideoId_fkey";
       ContentManagement          postgres    false    224    221    4754            �           2606    17582    Video Video_ChannelId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."Video"
    ADD CONSTRAINT "Video_ChannelId_fkey" FOREIGN KEY ("ChannelId") REFERENCES "UserManagement"."Channel"("Id");
 U   ALTER TABLE ONLY "ContentManagement"."Video" DROP CONSTRAINT "Video_ChannelId_fkey";
       ContentManagement          postgres    false    4746    217    221            �           2606    17693 +   VideoCategory VidoeCategory_CategoryId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."VideoCategory"
    ADD CONSTRAINT "VidoeCategory_CategoryId_fkey" FOREIGN KEY ("CategoryId") REFERENCES "ContentManagement"."Category"("Id") NOT VALID;
 f   ALTER TABLE ONLY "ContentManagement"."VideoCategory" DROP CONSTRAINT "VidoeCategory_CategoryId_fkey";
       ContentManagement          postgres    false    227    4752    220            �           2606    17688 (   VideoCategory VidoeCategory_VideoId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "ContentManagement"."VideoCategory"
    ADD CONSTRAINT "VidoeCategory_VideoId_fkey" FOREIGN KEY ("VideoId") REFERENCES "ContentManagement"."Video"("Id") NOT VALID;
 c   ALTER TABLE ONLY "ContentManagement"."VideoCategory" DROP CONSTRAINT "VidoeCategory_VideoId_fkey";
       ContentManagement          postgres    false    221    227    4754            �           2606    17534    Channel Channel_CreatorId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "UserManagement"."Channel"
    ADD CONSTRAINT "Channel_CreatorId_fkey" FOREIGN KEY ("CreatorId") REFERENCES "UserManagement"."User"("Id");
 V   ALTER TABLE ONLY "UserManagement"."Channel" DROP CONSTRAINT "Channel_CreatorId_fkey";
       UserManagement          postgres    false    4744    216    217            �           2606    17563 %   Notification Notification_UserId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "UserManagement"."Notification"
    ADD CONSTRAINT "Notification_UserId_fkey" FOREIGN KEY ("UserId") REFERENCES "UserManagement"."User"("Id");
 ]   ALTER TABLE ONLY "UserManagement"."Notification" DROP CONSTRAINT "Notification_UserId_fkey";
       UserManagement          postgres    false    4744    219    216            �           2606    17550 (   Subscription Subscription_ChannelId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "UserManagement"."Subscription"
    ADD CONSTRAINT "Subscription_ChannelId_fkey" FOREIGN KEY ("ChannelId") REFERENCES "UserManagement"."Channel"("Id");
 `   ALTER TABLE ONLY "UserManagement"."Subscription" DROP CONSTRAINT "Subscription_ChannelId_fkey";
       UserManagement          postgres    false    218    217    4746            �           2606    17545 +   Subscription Subscription_SubscriberId_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY "UserManagement"."Subscription"
    ADD CONSTRAINT "Subscription_SubscriberId_fkey" FOREIGN KEY ("SubscriberId") REFERENCES "UserManagement"."User"("Id");
 c   ALTER TABLE ONLY "UserManagement"."Subscription" DROP CONSTRAINT "Subscription_SubscriberId_fkey";
       UserManagement          postgres    false    4744    216    218            C   .  x����U1D��+��3�I��n)�a+*�q�W�zw���zt�L�N�����z��
SV����u���_����k���� �ڠ�*���mD%=���(MG<� l$�~Td�$4ǖ�Qϟ�������ח��=�a�6�a��['�s���[��z���0b�u8�(���b�)ǲR�H��??c��{�5�ϰ��\@cMqbU��)}�_op�ު0Cq8�F�lhqh[������ez���ʊP��) A�vg��y[ӷ߷{t�Y=��A%��6we��6��/���?�����p�      E     x��X�n#9<W}E�s�@2�d�}�S�a�`.|�XVCR���(?�6a��d%�����5j�J4Q]3!�j��d$�����q8_�˿N�c�N�5΍:�R�	�ؔ�����e*vj���7��`�O�dnl�[�\��L�g��c�qvqn�>��I�R�e�n�u5�Ufi��Wi-W���t�v^��{��m�<�A~8#>t��)6X�}��:i��������B�9'�|�f�F�S�3�N�c���4��������D�fM��)YK������d�x�1%sU;�z��	4�2錹J�����ok.��J�l��7�k2�0�� �J��\����m"�%��9����H�bR�oi�h�6\�C|��xa��K5:����f5������$$Mq!j�ޚ�:2������B�4�O<��p\j>�����l����s�i�{¹A�<���9��P����;qV)ͮsJ�"��y�،\h\Zv��t��aEW������.?,�|�\��k�bUKB���	Bʺ�-�$y���dE�O��x/
��M�M`L�$�y�-���G�<}�Ʒ�����sJ���݃&7��#�e�-�TS+�<ETƷ���1@D���'E�8ie#GRGsJō�@c'!t�q�XkGg�������t><|Y�������U�BO��)�,��5�z� \�[�%��[&Y���,lP/�e���$�>/���}��-������}ϗ��qe��o�~#xo�զ�R�C�N42�U}4ťf:Y�b��:��|��ZVAچ18reEֵ#�%u�-0���;����i���4(�-�'T��<�^tJE!�"+��^��ӧOۀ��I��6�-C�*���%q��}DY�*0�M&��k�V�O�N+X�����𧧼�����n�Al��u�����$(�HP$6��lֽ�;�R�ѱ�:蝏���b��׆Δb�n߸{<���~��c���K��-��U��Y�ίH�������r���!�{ᇬZ��g<C�q��KXJ�7Zl%,�[\!	��^��F�����)0Ҫ��3�Ф"h�Pa?!p���h��7S�u�BZ��R�6���9�X��
�:8�T��Bo�QP��]CC�k��m�W+��ҿ���jM��V��&���z4`�0~dZ�c�)�U�jDnXъ0�fWf�+�w�������I�4K���6۰Z�0ւ����-�R���h��kչF�}�����	�;�xo����L�W6���~��چ�G
��;��9¥��P���[@��bHН߹�j�� ����s_H�� �f�WkM^Mm��0"��Ї-�����/��\��9���y�Ai7�Z�W�v��g`�MJ�0�:�s1����b)e:>���k~x��������fx�%T�r佲(Q�p�,��WV>����P'ɔtv� x��	+�fZ+�vL���m�z���ƈ�9mù8�����W��"$4�Qp"x����b�p�hZqz*M�����c?_�3�*\X�m@�0'��2�bzؔR��I��v}7� h��@"<;X�1���&d�( �cTص��>j�?��卙o��~�[�*��>�U#�ӊ~a 'T�����d7է�t+��/n;��1~ׇ�5d�n=��s� ����8`��t7�$W�?F~n�i��1�9�?��ᆷСՒ1z��C�Q<:�|����!�e�u���1�g&���j)�#�I�H����R�եw��|>��L����b��!����:,C�}��P���4��*���}��C�{�.��ݒ�qy<A+a�?m�y[�cx~Q"�h�1��<�b�^GŶ��S���h�y�����鲌�����9���rY�u��_�>���E��+[I?Y��4`C�׸���ڒ�Dz7���Ěv�:�<�hn��]�_+��m`3(�TKm������y-���u���p�3�amX;B6�P��m��T���f�T��xE�T<����1�7<Ԡu ����q����I����*F��a!��6�����ү��N��Rm��$��n�(*�����t�@�a��y��\���a�R�|�.��6ķ� ֵ/H�N���n�����G      H   �  x���MnI���)��;$�dU�����/� ��� ���W�J�Z���W��;cv�j�ڄZ�è�RJs>�������?ϧӗ�4�?_�ݿ�/�:ϯ�c>^�Ϗ�y�PF�:�;^��[m+	Me�zbd̀�̸Kޓn�&zZ�O>��y��Vފ�󗗇���n�|�|��P&���V�62x�Ʈii���m�C��(��[��H!IYڕV��v,��FJY�|�)�,�I�A�,(�
"Rr�Fi������*`c�9�'�9�/�1?���s�$Y�z���B�M+���P�,�٪��}L�	���'A�M�04j(�Nw�D��Ȯ�)���Gk�hy���3guh�#Iش�7ě�%�ޭ�G�P>�ߢ�jV�������3mI�$���d0c����k*��0��o����4!P$'��}B7a	�{j�>��O�Yf<fĖ[�:�|�]��x�&t��D�	B=FZj�±���5�..��C��VF=��8x&��<��c��.]�-��)�ڈ��`�Ě�i���}�I�׾�Ox�3�zU�8�zL��T2�!�pI�
�2vx���M��S�]C�"��6�RL8y|1�*~�Wީl��X���F�kd�>��m�ȂȹHf����3Q��3,��"��fM��)�;vz��ž�,�|�n�)�}��f
x�Ceq�<P�����2�������
vL��P� �l�*�b|�b�	t��5>�p#ƞ춤�95������p��vEu�=�?��8�P6�z4)�"�li������p���O?�����r�����L)$ݑ�������H��s�\oJ}F�/�xb�q����sx���+�||��燗�}��~ۿ��z�[�H�_�{��������?��k      I     x��ѻq1�x�
7�I[�!P�%X/�C���� F���8@i����L8).�8e0�N�X�kl�G��R#+`�/��E�ȅ/��l�$�F�=�p��<��v������@c�a�@$i�&�M�y�_��X�0k�6	My�
M��:q��®�OF����W�\:aX)h��A���ob���"D�F�aZ�P��E1��}���z���'4�9V��:�:��ko���t_N��k#	=��5����[�/s~��I���������zi      F   �  x���Kr�H���)����
�YzS���c���l,/�^X��J�0�	m^ޗ�RsR��^F��jbgp�A_g�6*�*��,A������
����KH�P+L�a~[}�^ռ���̘�q�.�;�M��*N��V�$�io�+Ĩ�3�L:����bz��Z��>cP�OL��/&��g�c5	��v�6Je�4TSϑ��7�����g���!�~sT�%&N*��:G��0�W�Ti��t��Rj�Z�����}�[�m|�u���#qqáּts)}kl���O���]���3J]]��<�Cφ�ȁ���ֶ�<�I�7�����Q�����gT����I�MV��Rj�V�J�A�u��j�[�
�,V�ڱq�K�=���n/)�����2~hZ�E.r�g�L6븀���TR�ơ�G HH��o���n۫���k���.�J���#��%&~6[agH -��o��J����>6�&��'	�1��4K=0k���)r����y�W��=�*b�~Tg��D7�"��7׋pH��@�SP����B�1Z��i+9g�.�?�vEч�f��F��� %dQ�\p�V���& ��M�T�1�'��1�$*]P�Y��B��g�J�~�` h��{}��E ��l@J`V�R�,��D5�\��"B�1�$�1yc�V�5�J�A��c��ٲ~OAr�U/2��b�'�tk���2���d�M������3���k��B߫$+�];���;�/F��<��?VqN�>=f�⻳Ǉ���o�K	��0�����O�t/� s ���������AL_�"��J:������(�w�B������|�zrD�X�Ғ�Ć�:������qh�,�S�a<�z��^l�g�:y9m��3d����;<U"4�a~����d�����?	0�񐢮��*Ҳ�.�2	��S{��C�@�W%~�3n4��X�e�����,sv�����qz�`@z=��fln���P}��j�Vl��CHԒ�Q�O����ObZ.����a����Fl7�p��6�\�C5�V��%�a5���O�%Ze�:�Mb���'����A'.Y��������a�E'�e�Ű��h&���.E�oL��f����1�|�a�� n��@�u�680<��}��v�����3�D�xFq;O�^�Ĵ
���t|�=`o%,��V�^]���(v�      G   r  x��WA�-)������&z"�+��?����ު���]K���i�#4f�7��c����?�O.��F�G&�(�ʦ�_X����*X��4�E��4L����aGZ����հ�4v�E���K�,�Ѽ�s�e��*�ѤŇp��W�KJ�j��ۜ[�X�&��C������v�X��7Ζ�?���r�q�$9�1�=�^p6S9�����xҐ�aIb�Fd�p��ɇ��P����1��������ꯛ�ۢG�g�%_��B�t��\`݈<��g�뺓6�G���?��{�g~*8�ԝ<0�L���3�W����(�Kck�P��W3�x�b�U��(�X�9�1J�g���ig�}�yN�Y%V$��Z�o:qt�ɰɾ�������\�mY�	�l B�M�d��!C�Q���d�d�NG8w�c��Pe	kY�UЕ��E8r��(�y��fk��jk�\�舜8ǿ"wC��u�b��g�՚���E������%�<9�ܗ�Km?�޵%���m`M��4`�iB��I��hW�t�X�8�� C@�31�ɰ�Gۋf�9B%����=w��$F�5����F�ѢL��يĊۨVde�|�H��_�,�f`͡IO�ނf�ՈUSl���/���xr�j�Ұƚ�*�E5yJN�:�8�IB�0Q�!���pI��+K˭��U��C�n�~���R,$Ù��bKp�o��v_�Wq�U��Y]�*����<�g1�����㐿iؼ�m$��2�	ۅv8�����K���Q�_�;uI�ŝ[t$�j���H�)0�x�c_�R�U�}�W����)�7Xjcz5bvX�/�]>,H՛E	�����C��_+�y�`E#հjaQ�*��ZdQ�������"�      D      x��Zɒ��v]C_��s<��N�P�ɖ�l>V�|
ӡHd&�R��a�b)���[ＰW�x?���|n��G�ER��顀��p�97Ki��H\H�(H��yx����s��Z6M����6ՅnM�ٿ�Ӳ4҈����T��G�5;�k��c�՚�sr��eoE/笟kV��7ʦe�E{�+6�e���_���i��n�.�s�i��LM��X�u�Jô�
�2�QTj^d��ҢHb�� N��R�%��&Q4�7��$̝'o��M�d��/W���2r�/�L�����oB�J��
������E�z<��L���E:7O��c'͹��-LW��?:��ұ�,4����f����hE��m�~Kw�exƺ^�=~�Z,L=��nn���ffj�`�ci�.������;�z����]S�7�{�5�2J�gA�y$b�^�q�X)�J�[���f!z���+�k�0O�7�Y�z�-�� �ݞ���r�ؒ�����Qۚ�C���C�W��u�ް��32������C{<*Ҍ�(	y*�܋�D�^�<�`��G{�G��f��cX���h	�V��K%~�׿_�o��B���ߘ�t6�J�v:�[�YS��C���n/���oQ����ջmo������6J`�hUS�v .�jl��n.\'ҙW�(���[���R^�y R�+��,&�?�<7�2�K.Ϳ���5���?~���4�o8�[�BD$���E�c/�"aȳ"�y�OE"�DY:@�5��A,��b�×`��0���7�n�+����y����9��J��M�F��y��x4���i1���X����ƢC��H-E��ZQ0�V�[�~��[�){Q�؊(i>����X,-�h14;"ls��_A6	��8�����4�}�𕦶 �&Q\z@O-N���^�H��T��Ϛ{:Hiؑ�*v�6�����Hts���I�W�^طw yR�n]7�z���J�3��~.j^��B��Юwl	+� �)_�2'kE�Kr�RJ]�W5͢���6���X+���36�9,�V����Mm�X�(X'*�)@P��ޱ1�aM�8_ϖ΋=�dƚ�w��s�˄2E���yK��<y��4�*�$�'A�fi�d��ϵ�(�Q8��EP��|�%�i�鼤$�A�S�B�V
.zE|��������=M�����(}k��Q��xh�k�@`��HX��<�@x66jJ�n��-8aӳ�S���`@|�����fṮ�4�<�X�.�f�yTm�P�=�\�up.�� �8s.r^���]�4Y)}�M�c�7�������8�wX�?�����+Lk�h��^�%<
"�-��$-�X'eX8ρ���#ːD<��j�aJ��Dފ���{m�9G��!v{��fS���ύ<�S�K��eE���s@�����(��pd�_�X��{m*]�ED��'�k��^�D�r�]:�c#�/ �
��yk'��!��e�e�Q�dJwfV��c�k9����Y��h��XRTkLٍ �P=f�Ҵ�uRђ]�&/L��@>l�7ۣ��O:��,�Ό���:��3���o?4�ـ�A,,���k0V����t@5IF�&�����H��]��
Z�;7�鼵�h���'�s�hdq�nY7,�X��t��z�5vg+KG@�@�	��<?`��_��ߟ�w��K&q�fY�G���Ԩ��z�އ��kBK��8N}H�$-�qXU!e�#O	�}O�����	�>1�y��oé�CHv.��]e�~�������Ie�n�,�&�����c~�ƃ�F����� �[?������T��ϴR���(%�$dW^(�HrO���:��)?����yw��=G���}�8�{�%�ʤ�$L�>+��tm'e$E�@�{�nP���@`�#/*����[�����Gc����55ۀ � ?���-H'�B�خ%�AĞ!k����j }����!�v0�+g����������+����''���C��{�N^��ꁵ{1W��)�R�C�Tk��$��T&7H�-[q�B&��N�m;���'�nye��(�^���a���˹�A	Q���.��<}u6eG�d�k�|���h�Y���Sb�\�
�G��^���\��[�ͬ����;���g��e���kM�����CL�I��u���y�/'O��w��{��a�ڌ�|��t�!cd�Cel�k(�;�h^R.�G"�>�T��E��[����p���E�u�:� ��R����Č���Ǖ��dቊ� @,׾~��I
ӻ�����U����u��w�؋��._�e�?�ңϞ��4�xu��G4��о~���O;�-�)��v�P�w�n���fs�Ն��e�ȝMӃzh�8�l6�6�������ʼw_�����i@=C_g�Fip�Y������q��m?#@�d:���0@�c	_�*?W��'H��8ܚ��[0#����@���jh�m����W\4�ڒ�k��]���ݦ{�K�%�gK�E�3@H�թ��o�����QI���(v(�6b��`[z#F�C��Y��2DK� �x_�j��P��QT0i�V(#6/5�1"�B)��m��U�b�)���I�|��n%�7[��ŽT淇Q8$Z�qj�(E8�%�°�J�ʏ� �,p��^������g�������-��3p��tI�+j�J᯿�����t�B�DW��燻S�:Ѝ�9H"j>[[�LeE3�W#4��#���S��\���VD�D�
�B:J�[V���J&At����ñ����ԏS��sQ*@�J=^�W� ��$,��g��m����TW��sJ&�ӷF��ud�(��p�W09��|���.�⼁�٠��{:,f�:R��D�.g�X��&��[��`��=qu1���x�,�<@q�E0�Y��<."���"�o�D�L����,��;!����/�|6[�R$*�t�!�]����z��Od��Y�L�°�n(,��(uF�G%�"��E5���O���~�]��C-mM�������OqV�[�,�jdb�9���q��{-�O7'���E�^m�60\�c�3!��-m
�i�;�/H�7U%
R솊��H�˄��m�7!��1���\�Œ����	rp<i����Y�(#
UŰ�a�Ū[�NPD:�E�s�J
BC���l�̄L�[#ߟ���Ł������"�/b�V�Z��]�L pA� ��+�ѽS&�e��f���3Ў9V��s����0���pu��㽭�H���b=�ġ���,Իz�Z���|3�
ž�CQ:�#7����nk᳏pcዧ�m�H%"�베*G\�2���0/�<,Ŷ��_	������x�. ��6շͅ�z�������O|�'?�ӏ�>r�Oh0��H�GETO
�Q��ue�������D9Ӧj �.�%���������	;Cq>|��^����� �mC׌���v��|�
l.�o��k7�!)�A�͚ޕ�)yW-/�}gԋ�����,��Fw$?{F�@{�B�65�L�;�ai��P�����&㖤��}��4Ao��[��ԫ�q7��B�o�޶��J��%�[���vP�6Ŷ�Ra.M�b����{Ws���1.�� ��5h��P��"ms�jkùY�aiK0̲�v�����H�&�fi��zBѕ��斱Ӳ%UP蹡��.��1��ͮG��-�-��Z���6�O�؍B/L���� �F�7���KH�"/���GY�@3�S�1`\`����m��aǦK�u/N��C� ����Y����"괤o��� }�p���R�6��X�]�q����*��. %t)D��	�H�\T��l���r��[��F�oK:��\_����ȼo�� B�.H��n�Vp	��ߏB�\GN����vw+�jt'M�)Z�o�OZ�cZ��XN;�ӑ�l����Z]l#�f��&�}gDQ�����#hS�ŋ�Y�@{�*ԅ��2�ʤ��� �  �S��JT�, %����H�nk�L�ͼ4����W��a������(����<,b��U�("	�+�܋�P���\vb`��ܹD���޶�H A�l�EbWH��� ρ���yc:�o�ig�TN��@jNk��R�b�O�%H��� �VS�n�o��Ư3�\u9&���`�$�)�o<�)�mP�hq1�A,�������)�$^�o�Գ���[u�z����K-a0� Qf�����V�B,�Y�����.8�f�<��c����/�=���XL,J�v�NC�K��iۑ� �n/�,���t�F��B5� 
�KmR |Gty�2"����.�vĒeKB���u�/I�؟x���A�FI1��'�ԏt�}<���NI����o�����      J   �  x��V�#9�׽p�$R��r��Q�%����%�\�>xY0�4�t�8�G�UP
��X'�!?�ι��;�H��/t�=G�����?D��}&�l�!p���y�:��ސbf�"�Z#�Kp��_�����\���=�\1��� ����,\��&�QOGy�\-XZq���oHO��0s�=ZGrDP�	\��w�ʽo:=!�;Ndx��s�3�5���c��8_��������O��VUi���g����hE�I��IZ�)ͩǙ#c�?L�9���5�n7З5p�:�oH��u'�:{vi@�ڙ����)+�)�Ȗ`}�vn�5ll���?"	Ӿ��Է�e�����m�\����'��֞����ɭ�Y�����ɝ��=!=q�
�mxXI-���x!���b�u�qzBzⴷd�\�t���mJ�h���᧔�Hfw�$���x$C{�����rSy��޽��x7�m�~���U��N]�ӗ/�:[���Zv��bYI$�[<!��v��*�Xĭ���?$ھ��������md}=��X�j?����3��{Cz��h�`�CXní.^������n�Q�o�J>|
����Mp>��jv��=�:��%l�]��<�.����a�o:m�v�N��ǩ�g� 9[v�)����{B���UaV5'�~�:�v�o�չm�ى=���]e���)��v�&>��	������ۗP      @     x�]V]n�H~fN�����/٭7[��Ʊ�r0X ��?�')����\`���eO6'�#l5eg'�lVWW}��W�+i���*k*��S�
��0�KR��_������~�O�	�kɶ��7�bBf�w����������4D�G0�����V���P�L(�j�,b��B�#cu]|��F�a���[AG5�ҊP9�C����a}{sw�~�\7/���i�\.�c�ˢ��r�u��Pyj�T*�t�<yn��/�YhVrSk.O!1�k�i�cH���
�zp3u��z� 7N����zywu�"�7w�,on�~��[Q1m$8f�����yK�ORqТ���8�B��K��|����s����&C#�b2U06"4aJm�8Br��_HܯO$����H����%�Hpt��I���r!d�����8B�e����J%���_���t0QXW\��ڇU7�0���A7=/�f$ۮ�&�d��n�?��Md����񌄭\@��H���K�v�-�n$�mI�����~hcI~�14�0[�#z����B{q��D�F|`�C�ҡ=1���f��q�p:#��0�g�,�@��e>vt��x6��;ẗ́IVF'pj�WT^Q�=�LΩJ��
o�Z������ĥ6TT�[�ցZ���Jg�(j�w��˫�?tAm:r��C��\M�}:�k؀���;���"���7����_#i�չ���ax��`�cI.����C6�F�����^�"`��ۢD����0o����C�}�M�<e�F!�yl��S�MFspiw��ٟ�o��Dþ+$�vQE�aT�RS_;��RBaw;��w ���eͰ�D�{�d����f�MP�P2�U<����	}X�4�m;g�\3d`[��پ�ix*˒|�#�v����ܣ.}�[�%�7+�����������|�n9�M�"����YԵ�@Ъ
�BT�Sp�u���URإL���ڨ��)+P>Y��m������(WE��iqq�|�@���g��*M}4ș��4{��-I���Q*����[�Dm�0�u�(�R�\I%fE^A>Q������P���8,bq���<x���/od~#����T�Ag��\�.�\���a�pg��0Ns�Z7���l'9��H�0@�s���!�
�����ahp�I�?��C	AL��ÆD�zqZ%�u����Չ��W=�o�qF[0���u͟a��3Ap�����Y�|�)
{��3X[(�t�w�u_��A��o�����5v�yz�[>��r���S�;4�B~��/\N5�m�l�ے\�(���[����o3�=��SV�����fF�s �'�w���9�ql�-�,m�dm�Fj�iR������B�Z��i��&���N�Ҁ�M4�p�'T`��I͙[��}�"�������P�=��H��gFa��Yo򗱙PU瀧C�U�=�e��i<О����y�L��	#�%lq�������r�>#?�G���pFn�ߞ��ON���O@���<w5��i!q�@�,(�U�ZJ�2|V�
oX����P�l�8N@b�&�7�[Kq�0����ᶏK��|ȣu,��p��r#�6}�2�i|��	=��ˬ<�ʟ�n�T�*������:�G=KX��X�Ti�*lY-�,S/h�	�k'�-Qk�c�p�%k�2��@V�/0A�>���nu��&7���Ç�֑%�7��b�Y��]4ʠ<Fk��ū"�,6"/��V�⇫v��rY1���7o>�o޼���q�      B      x��[]o\Ǒ}����y�AWW�l�� �M�X{�0��"Bq�ʊ�����ɦ4w��-[��>�Uu�%7Č1��i�-��Y%�<K�&X���nZ��HHlr���:Yhx�����ϣ�w�w�}�������ͼq։��ؼstk�-�=�@~ɵ�"]Lh�I��ħ�B.�.\���& ��l��¾�>�3�"0�T&��*�Q�����&~O��u��1��51�-a�����"���.݄� b�F/ir��V8d#Q�A^�������~�D`@؋�	ŇbG24���b�K�-�N6̰q����������o����΃ ����)/q��S�Hk�	Mi�����BW�8���v����W��a�?�%�]���
���hĵ��H0%p�,�KL7�w;3v��IISLʂ�I�9 4ӥn���Dn	��z_��IS�"R�tC�Lf��4��,yoc���y��ވ��H�Nq�Ui~��O�K;�~=��߾�:������v�������~����~wx����[�q��B����iLd�$���$���c�K�]���O�(Kխ�J�@�$�jju_�y��Xd����}�,�e&��-�Ji&��VQ�k�x6�����g��HKIE(q	7�E���c-�:�K����@�v�����q��s��-	�b�KlL�%�F1����(z!c{�u����bI�����$�$&2�A\�
Uӵe%&IŽ,G{�,KN�M�p��h)���ؤ�C�LT�5u	���>����V�)3��:�-��P�=Z���{�FYЊjΞ�_�	==!dn�1�S�	�w AI}��j�j,������o_���ooN��cO�����%���ֳ���ѩh�=Y��4-G�����)����J����x��w�<#��C�^@�����4𞤎��n�21h1�� lZ?���E�E.-R��������4sF�Evc;��������O��%�%��Ei�ҭW1��7��5 �>�h%/�J������!� =��2��b0��I�q�X�,�r��J�Bp�/Bv���T��!{�Lq��ڜ(;B ����l@�A#��LzqY(/]
u��%���$�k��9�R�%�u0B@���<�6�8��1��%��FKp#yQ͋��Ϝb���1����*8G�=�dc�jUg�你�v�Cݪ3�&W��,�Q��%a�\����Ԡ>�ڢ	�m�t6;�A٨d���7-h� ⋮�	M�¬E;8�uX�2ɵS�/�?�� �쭕[�7���C��9��M��H�V܀(�~n�FϠy|uw�����d�s^ �}�Y���NJ������B�Kc�X[,��d�� �s]s0�*%Z�(ES����~��_ϴ;"��[y�6s��@_�v��&ٛj���&���d���f��!_=}��[���DhR��?<R�je�Ϫܬ%����ϕ�V�Gyg!�[�{H��2��{Gq�	YP��PFR�Γ��|�>��E��}L�--��6�sP gP^��px?{���� �T�O �����K�f�H'V���O�{�B�c������A:�U�� ��Ħq�Z��͹��VAfd�%�����7 {�Q=�(i4=zb
����	��lf �.e��҂�L��8w��nK��{�or 	��� *�S���T����{m��0��J�a���+�Cxv�Jm >Ǜ:t4Y��f��0Ͳ��^6��o-�),Y
K��z)Y��2�r��Gi���9!��'�G>(UR���P��[�}I|����e��?<g�!��
(-��bA�����(�j�Z� 5Kϥ�ttb,�����@� o���M�5�ޒ�k��\̣�9���C�����	,H)���|������'�� #�te���<��q9^��A�ϻ׻V��wځ�8��4���zJ1.���a)���.'LN�0��M�NJ� ������>�C�+��H���8a��>��v&N%������3���t��R���8B������xW�ȓ%zq�P�q%�l	��Tp�� �������=�7.�R�nT%5�zb5VлiX[8כ���DW
Ud��b���ͷ������7o����[��?��.�<��7=��۳��G'�C�3riښİcl��uq�}@@�*W =�]�"�#N� k�zRKO�Rq,�e�YP��D�' ��/������,!�[�R�^���_Rm'��!]���]���>�wߟ/{�S�Ki���PT��SЊ�*�b0�*t~*�����٤��~
h��<=�����><�Q�z�������Zt!h08�n�;�x��0��=�\rKm^�||��zA|i�M�[��z��4Y��XX�j����̅ �zX�a��R�y��T�|�qC���Hu��^��(�*�>}v�䀏z�L(�$��)�@@�6�
v���5������/�\����ӟ�tyҾ�Cp��	�^�\\���]���	3�Ȭ�~]��*�ޕ�ӯ�n���Y�P4�d���BE{c�JP��@r[h��z%�G/Ry�\P�/����U���-���XH0+�
[�b�pHD������^��z��F'�Ƀ�[.7�p��[��M�(�LhY�h�B����p:�`�-9z^"<;�7Rީ^�5C���D�<c�q^����p|,���Zw��������B�u��-���m�f¬��=��]�Ai�s�+�n����e#=Ʉ9ȉ5y��\��(P�s(FT�6,��W��}�=m��S1R��2@C��2d	r��g[]�W�qG��l������NX�ui �M�Y��a����y%%���6��58H'�ֻ0�����nD�UK��s��<�D�/�#�!V�V!LW�6|$��:�������l%�8>{��v����@S=U9�NV�>���������
ֲ�`�;��!5q�t3��U؆^��=�`���4z%+��6����x�Y�{̻v>������?A��i
�
�r�P�e#2i���������q7~���9�ݎW%�^�(�s�m���%��i�3��~�݀�h$�z���Et�H���&�%�8k+���C���{O������9U�{��D���jY�VS҈��d��`n��Z��@=��������y!�:��L$h���}�q��Pݼ�V�XX+1@�Ci2�Xc�|AI&AN��I�e�\�%^�S�����1��Y�A���*&����K�s#�}6�~Fz���k�Y\��C	�FE/ƻ�;%�1�jk�m���ŸB�!��a��l�n���������T��ΕA�sthܫS,h"���q�w�ێ�/���@>x$X	�wV�1���:�&�%�|��ٮ`�^�EZ/4#*]`��Bl�D%�8��!Ǘ#{g��BaX�z�E�D[{"C����s*׀��L��������ûs�#�\vvnZ�yl/:���>�Z(".9�@��_�(�������w� ����b��䊢��3��zǞutk |q"�(y;� ���	K�[�zAI���C�υ��uH���v�Cm����/��*�^k(��d<���L=����v4`�/�\Y��3_�oK�|�#l&y�j����ڋ��Q�W�e� qA�|�P�!�B-Y?P�)&lHFM�Ѳ#�9��W�Wa	j�ɲ}�`�ИM��b�U�^]g�z[�P�/�E9G�dF�2�i�ie`�RTO:(�e����+���Ƈ�}(��vСH��4��Ȧ�G�	5{��I���%�E�x�qz�+� b�M��Uf� �Qe�p<|��q�7����I�93$�QM�Oy���DXCՔt�P�LNZ����.�]Z��:�i�	�饷�zx�����q j���X·.餳g��^
T��p�W��������46)�q����v��N�ه��3�c�*�t�,/ui�v{��Uupȷ�6e��� �  �3o��^�����mk:g�be�7�t�z)��ًI�V�7��K���ӤM$��/a�αS��`���:x�@Ub$l��}vojy����ٍIJO_�DÇ���`b�v�FӱX�xo��i�|)��;��z�G��\am�������n� �<�6��������z,���������ٝ˫�*�%c�BG���R"$;X�y&F��Kaoe)�̀	���Rc�+�B�g�6�a�2�9@��m}�*^���OJ6�V�O�:��2)��y�w6���Lʫ4O����TB�h���AF�����[Fa�{6�Noګ]�7���\w�<s�::�A1'��~��`p}��T��w�܅:���{��7�ÏK�����^���wW@�%zAga��H�[�g=w��~b���K��xF�2ul�kdu�v=��.��?K���Km�����0�^��Bj�;F�>W�X����Ir,vȤ�B�?�~�px\� �U����L���M�ٳ�R ����������i7����+>����n�C#��?����O&M�s6�u�r�]��o]qz5M<t����'M�ݘd!F�\�T_|��'H+�)8�L
^6h7lbm�<`u�;g4��>�uc ��[�O!B+�9(��c�t�IM>������}SB"����щ,�w����k/z�W\��t!���^�@�J�uBe�i~�/�*V��k͹*q��޳�^�z|����}��ٔvO	�,-c i�*5�	p^��l��q�q���O�Ӽߗ���y�c� v7jH�9�����D��ћ �%���Ƴ�+b���$�,z
�+��[���\d�V�3s�?n�w��п*����8n)��oBS ��)[ѱ(1M.�:NK��bE��m��{�=�m;#zr���\_����k�N���;-�X�����n�}ә��b뼾��.Y�Ղ:���x�}D2$Ki�0�H}��Y"u�o�A&����wG�0�Y`���g���|��Vlh,�C��%ؒ��WjG;}�%W��̜�Bs�4�ʤ��SJ�b��U���EZc�
%SK+MFB�����e=g�'��w���V�+�'�i��1�����:׃y�N��>gc�o��mp�x����,�1xe      A     x���K��8Dו����O"��F������ ���Hx��LF(�:Kmk+4g��o* 껌B�����&e1�:6��f��f�~1�`�b���+��W=���x5o�Q��N���z�\,j�_�8�]�s�5�-��k��������6v����{A�|L뽖ᓣ�!�Exk�R/�R^�aiS��)���*k�Ѥ�s�j��U�="g�M#FƠa���BZ�d��/��E)��|5�ذ�)�&�>�Q�¾��oM���j��Ƒr�r��i�5���1�g%o�˫�a��I��Z���9 {��#���J"~�%��At9��
#�s�N��.��6���J��x�Ry�I�jy*s��^�Ԇ͞��z�R�t#7J:��<[����|i�O��DW��g�X8!���LqX�O��X���E��Qv����t���c�s��&��],c��9�۳�*4���b����u���v�(�}��q�no���A�������]�Na�+BC��8��ִ�^$�	�&� �kBJF}�+(���K��\���T�0L�AW���v�K9�<N�oH
/A��8V�����I�75Q�W�ca�Nkm]��{��;X�o_���O!?�$!%�U�U;���ۃߋj�4f��=J�0uj�ɟ�dҧ�%7<C<N�D�[�b97B8�1A�Z�|��k-�����iˉ,����O�"e:��;��F��&43cz���A�eכ�IEԅ� ����x�!����z�^ ^���      ?   �  x����n�8�����P%�Ó��1���n��w<&n����M��;��jc@�`��P��p��d��O23�"gh�b��̢F����W>O���g����Ns��J��h4H�6�3-���SQ�d�u�k,{7<��bY����iƁq;��p@��n����R ���Ǖ��wy���/����r�u�5 9��=�hR�����\�7���E�����{�Lp&�%l)�����Ch:l/P����-C�$� ��`KR��UP]_O'��˶̻�h�WOg������_BU�H���w�#��5�����b�B��sS9����.o��<��o����|o����m2�eK�'�h�6���F�ȇ u��SȢ�Y�iM�	i���&RP�S��-�_�?���Z>���p�gɄ�����������t�N��p��L��@� ra<������_�77���^/�Ջ|��>���V�.�N�t-�4L���t�A'��Q��3�7*���/r�C.Zau�NS�����N��5>�����]Gi.�K� ��u�H>h/�*��e�'$(ȬC�\�hu�����)OfO������w�E��y79?��'J��m��X�E�S��[j�'�6Hs�F����a날9��K� ')�V2�c*�"+�NU���L;t��F=8�cvJ..�ռA�HU+��:bY�3)�3E�[�y�:0[�b�BCkt�������������)�1'c4�>�m7W���-5U�m��ШN��;)��TF�8鞧��a�*"���OyW����h؃�>��d�BK"ږd-��ϑs����-���s.��.4�]Z��H^�n��{^Nn�wy���U~�&��������~8ty�mb�T���'M@م����&�Ij�H��3�4��Y8�Pݯ����oړv��5: =w���fp�̡m�@�`�:��!@�������Y�_N     