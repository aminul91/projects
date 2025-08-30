cmake_minimum_required(VERSION 2.8)

project(pracc)
add_executable(${PROJECT_NAME} "main.cpp")
find_package(Threads REQUIRED)
target_link_libraries(${PROJECT_NAME} Threads::Threads)



set(CMAKE_BUILD_TYPE Debug)
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -g")
