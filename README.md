📦 System Integration 445 - NIS
🎯 Giới thiệu
Đây là Case Study 2 trong môn học Tích hợp hệ thống (System Integration), với 3 thành phần chính:

Spring App (port 8080) — Quản lý dữ liệu Employee

HR App (C#) (port 19355) — Quản lý dữ liệu Personal

Spring Show (port 8888) — Hiển thị dữ liệu gộp từ hai hệ thống trên, có tích hợp WebSocket và Redis

🚀 Hướng dẫn cài đặt và chạy
🐬 1. Khởi tạo MySQL bằng Docker:
docker run --name mysql-springapp -e MYSQL_ROOT_PASSWORD=123 -p 3308:3306 -d mysql:5.6

🔧 Sau đó cập nhật cấu hình kết nối trong file hibernate.cfg.xml như sau:
<property name="connection.url">jdbc:mysql://localhost:3308/your_database</property>
<property name="connection.username">root</property>
<property name="connection.password">123</property>

🧠 2. Khởi tạo Redis bằng Docker
docker run --name redis --network spring-redis-net -p 6379:6379 redis
📌 Redis dùng để cache dữ liệu merged giữa Employee và Personal giúp tăng tốc độ hiển thị dữ liệu trên giao diện.

🧩 3. Cấu trúc hệ thống
Tên Ứng Dụng	    Cổng	         Mô tả
Spring App	      8080	     Quản lý và cung cấp API Employee
HR App	          19355	     Quản lý và cung cấp API Personal
Spring Show	      8888	     Hiển thị bảng dữ liệu Merged từ 2 app trên, hỗ trợ WebSocket & Redis
🔄 Chức năng nổi bật
✅ Gộp dữ liệu Employee và Personal theo idEmployee hoặc fullName

✅ Hiển thị bảng động sử dụng Tabulator

✅ Tích hợp Redis để cache dữ liệu

✅ Tích hợp WebSocket (SockJS + STOMP) để cập nhật realtime khi có dữ liệu mới
